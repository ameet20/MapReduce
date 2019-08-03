package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;

/**
 * Enum representing an AccessControlList that drives set of operations that
 * can be performed on a queue.
 */
@InterfaceAudience.Private
public enum QueueACL {
  SUBMIT_JOB ("acl-submit-job"),
  ADMINISTER_JOBS ("acl-administer-jobs");
  // Currently this ACL acl-administer-jobs is checked for the operations
  // FAIL_TASK, KILL_TASK, KILL_JOB, SET_JOB_PRIORITY and VIEW_JOB.

  // TODO: Add ACL for LIST_JOBS when we have ability to authenticate
  //       users in UI
  // TODO: Add ACL for CHANGE_ACL when we have an admin tool for
  //       configuring queues.

  private final String aclName;

  QueueACL(String aclName) {
    this.aclName = aclName;
  }

  public final String getAclName() {
    return aclName;
  }
}
