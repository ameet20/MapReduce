package org.apache.hadoop.mapred;
import org.apache.hadoop.tools.rumen.JobStory;

/**
 * {@link SimulatorEvent} for trigging the submission of a job to the job tracker.
 */
public class JobSubmissionEvent extends SimulatorEvent {
  private final JobStory job;
  
  public JobSubmissionEvent(SimulatorEventListener listener, long timestamp,  
                            JobStory job) {
    super(listener, timestamp);
    this.job = job;
  }
  
  public JobStory getJob() {
    return job;
  }

  @Override
  protected String realToString() {
    return super.realToString() + ", jobID=" + job.getJobID();
  }
}
