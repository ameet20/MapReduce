package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.*;

/**
 * Job related ACLs
 */
@InterfaceAudience.Private
public enum JobACL {

  /**
   * ACL for 'viewing' job. Dictates who can 'view' some or all of the job
   * related details.
   */
  VIEW_JOB(MRJobConfig.JOB_ACL_VIEW_JOB),

  /**
   * ACL for 'modifying' job. Dictates who can 'modify' the job for e.g., by
   * killing the job, killing/failing a task of the job or setting priority of
   * the job.
   */
  MODIFY_JOB(MRJobConfig.JOB_ACL_MODIFY_JOB);

  String aclName;

  JobACL(String name) {
    this.aclName = name;
  }

  /**
   * Get the name of the ACL. Here it is same as the name of the configuration
   * property for specifying the ACL for the job.
   * 
   * @return aclName
   */
  public String getAclName() {
    return aclName;
  }
}
