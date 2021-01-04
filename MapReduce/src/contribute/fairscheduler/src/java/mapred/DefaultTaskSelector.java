package org.apache.hadoop.mapred;

import java.io.IOException;

/**
 * A {@link TaskSelector} implementation that wraps around the default
 * {@link JobInProgress#obtainNewMapTask(TaskTrackerStatus, int)} and
 * {@link JobInProgress#obtainNewReduceTask(TaskTrackerStatus, int)} methods
 * in {@link JobInProgress}, using the default Hadoop locality and speculative
 * threshold algorithms.
 */
public class DefaultTaskSelector extends TaskSelector {

  @Override
  public int neededSpeculativeMaps(JobInProgress job) {
    int count = 0;
    long time = System.currentTimeMillis();
    for (TaskInProgress tip: job.maps) {
      if (tip.isRunning() && tip.canBeSpeculated(time)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int neededSpeculativeReduces(JobInProgress job) {
    int count = 0;
    long time = System.currentTimeMillis();
    double avgProgress = job.getStatus().reduceProgress();
    for (TaskInProgress tip: job.reduces) {
      if (tip.isRunning() && tip.canBeSpeculated(time)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public Task obtainNewMapTask(TaskTrackerStatus taskTracker, JobInProgress job,
      int localityLevel) throws IOException {
    ClusterStatus clusterStatus = taskTrackerManager.getClusterStatus();
    int numTaskTrackers = clusterStatus.getTaskTrackers();
    return job.obtainNewMapTask(taskTracker, numTaskTrackers,
        taskTrackerManager.getNumberOfUniqueHosts(), localityLevel);
  }

  @Override
  public Task obtainNewReduceTask(TaskTrackerStatus taskTracker, JobInProgress job)
      throws IOException {
    ClusterStatus clusterStatus = taskTrackerManager.getClusterStatus();
    int numTaskTrackers = clusterStatus.getTaskTrackers();
    return job.obtainNewReduceTask(taskTracker, numTaskTrackers,
        taskTrackerManager.getNumberOfUniqueHosts());
  }

}
