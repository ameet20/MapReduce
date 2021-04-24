package org.apache.hadoop.mapred;

/**
 * {@link JobCompleteEvent} is created by {@link SimulatorJobTracker} when a job
 * is completed. {@link SimulatorJobClient} picks up the event, and mark the job
 * as completed. When all jobs are completed, the simulation is terminated.
 */
public class JobCompleteEvent extends SimulatorEvent {

  private SimulatorEngine engine;
  private JobStatus jobStatus;

  public JobCompleteEvent(SimulatorJobClient jc, long timestamp, 
                          JobStatus jobStatus, SimulatorEngine engine) {
    super(jc, timestamp);
    this.engine = engine;
    this.jobStatus = jobStatus;
  }

  public SimulatorEngine getEngine() {
    return engine;
  }

  public JobStatus getJobStatus() {
    return jobStatus;
  }

  @Override
  protected String realToString() {
    return super.realToString()+", status=("+jobStatus.toString()+")";
  }
}
