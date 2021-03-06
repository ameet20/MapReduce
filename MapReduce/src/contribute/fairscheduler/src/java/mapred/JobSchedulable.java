package org.apache.hadoop.mapred;

import java.io.IOException;
import java.util.Collection;

import org.apache.hadoop.mapred.FairScheduler.JobInfo;
import org.apache.hadoop.mapreduce.TaskType;

public class JobSchedulable extends Schedulable {
  private FairScheduler scheduler;
  private JobInProgress job;
  private TaskType taskType;
  private int demand = 0;

  public JobSchedulable(FairScheduler scheduler, JobInProgress job, 
      TaskType taskType) {
    this.scheduler = scheduler;
    this.job = job;
    this.taskType = taskType;
  }
  
  @Override
  public String getName() {
    return job.getJobID().toString();
  }

  public JobInProgress getJob() {
    return job;
  }
  
  @Override
  public void updateDemand() {
    demand = 0;
    if (isRunnable()) {
      // For reduces, make sure enough maps are done that reduces can launch
      if (taskType == TaskType.REDUCE && !job.scheduleReduces())
        return;
      // Add up demand from each TaskInProgress; each TIP can either
      // - have no attempts running, in which case it demands 1 slot
      // - have N attempts running, in which case it demands N slots, and may
      //   potentially demand one more slot if it needs to be speculated
      TaskInProgress[] tips = (taskType == TaskType.MAP ? 
          job.getTasks(TaskType.MAP) : job.getTasks(TaskType.REDUCE));
      boolean speculationEnabled = (taskType == TaskType.MAP ?
          job.hasSpeculativeMaps() : job.hasSpeculativeReduces());
      long time = scheduler.getClock().getTime();
      for (TaskInProgress tip: tips) {
        if (!tip.isComplete()) {
          if (tip.isRunning()) {
            // Count active tasks and any speculative task we want to launch
            demand += tip.getActiveTasks().size();
            if (speculationEnabled && tip.canBeSpeculated(time))
              demand += 1;
          } else {
            // Need to launch 1 task
            demand += 1;
          }
        }
      }
    }
  }

  private boolean isRunnable() {
    JobInfo info = scheduler.getJobInfo(job);
    int runState = job.getStatus().getRunState();
    return (info != null && info.runnable && runState == JobStatus.RUNNING);
  }

  @Override
  public int getDemand() {
    return demand;
  }
  
  @Override
  public void redistributeShare() {}

  @Override
  public JobPriority getPriority() {
    return job.getPriority();
  }

  @Override
  public int getRunningTasks() {
    if (!job.inited()) {
      return 0;
    }
    return taskType == TaskType.MAP ? job.runningMaps() : job.runningReduces();
  }

  @Override
  public long getStartTime() {
    return job.startTime;
  }
  
  @Override
  public double getWeight() {
    return scheduler.getJobWeight(job, taskType);
  }
  
  @Override
  public int getMinShare() {
    return 0;
  }

  @Override
  public Task assignTask(TaskTrackerStatus tts, long currentTime,
      Collection<JobInProgress> visited) throws IOException {
    if (isRunnable()) {
      visited.add(job);
      TaskTrackerManager ttm = scheduler.taskTrackerManager;
      ClusterStatus clusterStatus = ttm.getClusterStatus();
      int numTaskTrackers = clusterStatus.getTaskTrackers();

      // check with the load manager whether it is safe to 
      // launch this task on this taskTracker.
      LoadManager loadMgr = scheduler.getLoadManager();
      if (!loadMgr.canLaunchTask(tts, job, taskType)) {
        return null;
      }
      if (taskType == TaskType.MAP) {
        LocalityLevel localityLevel = scheduler.getAllowedLocalityLevel(
            job, currentTime);
        scheduler.getEventLog().log(
            "ALLOWED_LOC_LEVEL", job.getJobID(), localityLevel);
        // obtainNewMapTask needs to be passed 1 + the desired locality level
        return job.obtainNewMapTask(tts, numTaskTrackers,
            ttm.getNumberOfUniqueHosts(), localityLevel.toCacheLevelCap());
      } else {
        return job.obtainNewReduceTask(tts, numTaskTrackers,
            ttm.getNumberOfUniqueHosts());
      }
    } else {
      return null;
    }
  }
}
