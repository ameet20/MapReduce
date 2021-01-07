package org.apache.hadoop.mapred;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.hadoop.mapreduce.TaskType;

/**
 * A schedulable pool of jobs.
 */
public class Pool {
  /** Name of the default pool, where jobs with no pool parameter go. */
  public static final String DEFAULT_POOL_NAME = "default";
  
  /** Pool name. */
  private String name;
  
  /** Jobs in this specific pool; does not include children pools' jobs. */
  private Collection<JobInProgress> jobs = new ArrayList<JobInProgress>();
  
  /** Scheduling mode for jobs inside the pool (fair or FIFO) */
  private SchedulingMode schedulingMode;

  private PoolSchedulable mapSchedulable;
  private PoolSchedulable reduceSchedulable;

  public Pool(FairScheduler scheduler, String name) {
    this.name = name;
    mapSchedulable = new PoolSchedulable(scheduler, this, TaskType.MAP);
    reduceSchedulable = new PoolSchedulable(scheduler, this, TaskType.REDUCE);
  }
  
  public Collection<JobInProgress> getJobs() {
    return jobs;
  }
  
  public void addJob(JobInProgress job) {
    jobs.add(job);
    mapSchedulable.addJob(job);
    reduceSchedulable.addJob(job);
  }
  
  public void removeJob(JobInProgress job) {
    jobs.remove(job);
    mapSchedulable.removeJob(job);
    reduceSchedulable.removeJob(job);
  }
  
  public String getName() {
    return name;
  }

  public SchedulingMode getSchedulingMode() {
    return schedulingMode;
  }
  
  public void setSchedulingMode(SchedulingMode schedulingMode) {
    this.schedulingMode = schedulingMode;
  }

  public boolean isDefaultPool() {
    return Pool.DEFAULT_POOL_NAME.equals(name);
  }
  
  public PoolSchedulable getMapSchedulable() {
    return mapSchedulable;
  }
  
  public PoolSchedulable getReduceSchedulable() {
    return reduceSchedulable;
  }
  
  public PoolSchedulable getSchedulable(TaskType type) {
    return type == TaskType.MAP ? mapSchedulable : reduceSchedulable;
  }
}
