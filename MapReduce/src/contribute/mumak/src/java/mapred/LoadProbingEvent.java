package org.apache.hadoop.mapred;

/**
 * {@link LoadProbingEvent} is created by {@link SimulatorJobTracker} when the
 * {@link SimulatorJobSubmissionPolicy} is STRESS. {@link SimulatorJobClient}
 * picks up the event, and would check whether the system load is stressed. If
 * not, it would submit the next job.
 */
public class LoadProbingEvent extends SimulatorEvent {
  public LoadProbingEvent(SimulatorJobClient jc, long timestamp) {
    super(jc, timestamp);
  }
}
