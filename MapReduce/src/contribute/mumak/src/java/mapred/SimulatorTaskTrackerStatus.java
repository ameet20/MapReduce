package org.apache.hadoop.mapred;

import java.util.List;

/**
 * This class only exists to pass the current simulation time to the 
 * JobTracker in the heartbeat() call.
 */
class SimulatorTaskTrackerStatus extends TaskTrackerStatus {
  /**
   * The virtual, simulation time, when the hearbeat() call transmitting 
   * this TaskTrackerSatus occured. 
   */
  private final long currentSimulationTime;

  /**
   * Constructs a SimulatorTaskTrackerStatus object. All parameters are 
   * the same as in {@link TaskTrackerStatus}. The only extra is
   * @param currentSimulationTime the current time in the simulation when the 
   *                              heartbeat() call transmitting this 
   *                              TaskTrackerStatus occured.
   */ 
  public SimulatorTaskTrackerStatus(String trackerName, String host, 
                                    int httpPort, List<TaskStatus> taskReports, 
                                    int failures, int maxMapTasks,
                                    int maxReduceTasks,
                                    long currentSimulationTime) {
    super(trackerName, host, httpPort, taskReports,
          failures, maxMapTasks, maxReduceTasks);
    this.currentSimulationTime = currentSimulationTime;
  }

  /** 
   * Returns the current time in the simulation.
   */
  public long getCurrentSimulationTime() {
    return currentSimulationTime;
  }
}
