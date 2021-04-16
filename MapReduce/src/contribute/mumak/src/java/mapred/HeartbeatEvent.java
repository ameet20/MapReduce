package org.apache.hadoop.mapred;

/**
 * This class is used by {@link SimulatorTaskTracker}s for signaling themselves
 * when the next hearbeat() call to the JobTracker is due.
 */
class HeartbeatEvent extends SimulatorEvent {
  /**
   * Constructor.
   * 
   * @param listener
   *          the {@link SimulatorTaskTracker} this event should be delivered to
   * @param timestamp
   *          the time when this event is to be delivered
   */
  public HeartbeatEvent(SimulatorEventListener listener, long timestamp) {
    super(listener, timestamp);
  }

}
