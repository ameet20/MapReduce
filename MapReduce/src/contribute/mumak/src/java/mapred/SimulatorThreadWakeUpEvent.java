package org.apache.hadoop.mapred;

public class SimulatorThreadWakeUpEvent extends SimulatorEvent {

  public SimulatorThreadWakeUpEvent(SimulatorEventListener listener, 
      long timestamp) {
    super(listener, timestamp);
  }

}
