package org.apache.hadoop.mapred;

/**
 * {@link SimulatorEvent} represents a specific event in Mumak. 
 * 
 *  Each {@link SimulatorEvent} has an expected expiry time at which it is fired
 *  and an {@link SimulatorEventListener} which will handle the {@link SimulatorEvent} when
 *  it is fired.
 */
public abstract class SimulatorEvent {
  protected final SimulatorEventListener listener;
  protected final long timestamp;
  protected long internalCount;

  protected SimulatorEvent(SimulatorEventListener listener, long timestamp) {
    this.listener = listener;
    this.timestamp = timestamp;
  }
  
  /**
   * Get the expected event expiry time. 
   * @return the expected event expiry time
   */
  public long getTimeStamp() {
    return timestamp;
  }

  /**
   * Get the {@link SimulatorEventListener} to handle the {@link SimulatorEvent}.
   * @return the {@link SimulatorEventListener} to handle the {@link SimulatorEvent}.
   */
  public SimulatorEventListener getListener() {
    return listener;
  }

  /**
   * Get an internal counter of the {@link SimulatorEvent}. Each {@link SimulatorEvent} holds a
   * counter, incremented on every event, to order multiple events that occur
   * at the same time.
   * @return internal counter of the {@link SimulatorEvent}
   */
  long getInternalCount() {
    return internalCount;
  }
  
  /**
   * Set the internal counter of the {@link SimulatorEvent}.
   * @param count value to set the internal counter
   */
  void setInternalCount(long count) {
    this.internalCount = count;
  }
  
  @Override
  public String toString() {
    return this.getClass().getName() + "[" + realToString() + "]";
  }
    
  /**
   * Converts the list of fields and values into a human readable format;
   * it does not include the class name.
   * Override this if you wanted your new fields to show up in toString().
   *
   * @return String containing the list of fields and their values.
   */
  protected String realToString() {
    return "timestamp=" + timestamp + ", listener=" + listener;
  }
}
