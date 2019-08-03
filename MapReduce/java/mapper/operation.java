package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.mapreduce.JobACL;

/**
 * Generic operation that maps to the dependent set of ACLs that drive the
 * authorization of the operation.
 */
@InterfaceAudience.Private
public enum Operation {
  VIEW_JOB_COUNTERS(QueueACL.ADMINISTER_JOBS, JobACL.VIEW_JOB),
  VIEW_JOB_DETAILS(QueueACL.ADMINISTER_JOBS, JobACL.VIEW_JOB),
  VIEW_TASK_LOGS(QueueACL.ADMINISTER_JOBS, JobACL.VIEW_JOB),
  KILL_JOB(QueueACL.ADMINISTER_JOBS, JobACL.MODIFY_JOB),
  FAIL_TASK(QueueACL.ADMINISTER_JOBS, JobACL.MODIFY_JOB),
  KILL_TASK(QueueACL.ADMINISTER_JOBS, JobACL.MODIFY_JOB),
  SET_JOB_PRIORITY(QueueACL.ADMINISTER_JOBS, JobACL.MODIFY_JOB),
  SUBMIT_JOB(QueueACL.SUBMIT_JOB, null);
  
  public QueueACL qACLNeeded;
  public JobACL jobACLNeeded;
  
  Operation(QueueACL qACL, JobACL jobACL) {
    this.qACLNeeded = qACL;
    this.jobACLNeeded = jobACL;
  }
}
© 2019 GitHub, Inc.
