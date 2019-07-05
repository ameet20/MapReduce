package org.apache.hadoop.mapred;

/**
 * {@link JobChangeEvent} is used to capture state changes in a job. A job can 
 * change its state w.r.t priority, progress, run-state etc.
 */
abstract class JobChangeEvent {
  private JobInProgress jip;
  
  JobChangeEvent(JobInProgress jip) {
    this.jip = jip;
  }
  
  /**
   * Get the job object for which the change is reported
   */
  JobInProgress getJobInProgress() {
    return jip;
  }
}
