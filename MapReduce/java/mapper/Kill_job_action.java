package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


/**
 * Represents a directive from the {@link org.apache.hadoop.mapred.JobTracker} 
 * to the {@link org.apache.hadoop.mapred.TaskTracker} to kill the task of 
 * a job and cleanup resources.
 * 
 */
class KillJobAction extends TaskTrackerAction {
  final JobID jobId;

  public KillJobAction() {
    super(ActionType.KILL_JOB);
    jobId = new JobID();
  }

  public KillJobAction(JobID jobId) {
    super(ActionType.KILL_JOB);
    this.jobId = jobId;
  }
  
  public JobID getJobID() {
    return jobId;
  }
  
  @Override
  public void write(DataOutput out) throws IOException {
    jobId.write(out);
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    jobId.readFields(in);
  }

}
