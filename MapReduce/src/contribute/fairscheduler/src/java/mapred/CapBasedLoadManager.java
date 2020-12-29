package org.apache.hadoop.mapred;

import org.apache.hadoop.mapreduce.TaskType;
import org.apache.hadoop.conf.Configuration;

/**
 * A {@link LoadManager} for use by the {@link FairScheduler} that allocates
 * tasks evenly across nodes up to their per-node maximum, using the default
 * load management algorithm in Hadoop.
 */
public class CapBasedLoadManager extends LoadManager {
  
  float maxDiff = 0.0f;
  
  public void setConf(Configuration conf) {
    super.setConf(conf);
    maxDiff = conf.getFloat("mapred.fairscheduler.load.max.diff", 0.0f);
  }
  
  /**
   * Determine how many tasks of a given type we want to run on a TaskTracker. 
   * This cap is chosen based on how many tasks of that type are outstanding in
   * total, so that when the cluster is used below capacity, tasks are spread
   * out uniformly across the nodes rather than being clumped up on whichever
   * machines sent out heartbeats earliest.
   */
  int getCap(int totalRunnableTasks, int localMaxTasks, int totalSlots) {
    double load = maxDiff + ((double)totalRunnableTasks) / totalSlots;
    return (int) Math.ceil(localMaxTasks * Math.min(1.0, load));
  }

  @Override
  public boolean canAssignMap(TaskTrackerStatus tracker,
      int totalRunnableMaps, int totalMapSlots) {
    return tracker.countMapTasks() < getCap(totalRunnableMaps,
        tracker.getMaxMapSlots(), totalMapSlots);
  }

  @Override
  public boolean canAssignReduce(TaskTrackerStatus tracker,
      int totalRunnableReduces, int totalReduceSlots) {
    return tracker.countReduceTasks() < getCap(totalRunnableReduces,
        tracker.getMaxReduceSlots(), totalReduceSlots);
  }

  @Override
  public boolean canLaunchTask(TaskTrackerStatus tracker,
      JobInProgress job,  TaskType type) {
    return true;
  }
}
