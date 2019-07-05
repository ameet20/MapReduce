package org.apache.hadoop.mapred;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.mapreduce.JobACL;
import org.apache.hadoop.mapreduce.MRConfig;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.authorize.AccessControlList;

@InterfaceAudience.Private
class JobACLsManager {

  JobConf conf;

  public JobACLsManager(JobConf conf) {
    this.conf = conf;
  }

  boolean areACLsEnabled() {
    return conf.getBoolean(MRConfig.MR_ACLS_ENABLED, false);
  }

  /**
   * Construct the jobACLs from the configuration so that they can be kept in
   * the memory. If authorization is disabled on the JT, nothing is constructed
   * and an empty map is returned.
   * 
   * @return JobACL to AccessControlList map.
   */
  Map<JobACL, AccessControlList> constructJobACLs(JobConf conf) {

    Map<JobACL, AccessControlList> acls =
        new HashMap<JobACL, AccessControlList>();

    // Don't construct anything if authorization is disabled.
    if (!areACLsEnabled()) {
      return acls;
    }

    for (JobACL aclName : JobACL.values()) {
      String aclConfigName = aclName.getAclName();
      String aclConfigured = conf.get(aclConfigName);
      if (aclConfigured == null) {
        // If ACLs are not configured at all, we grant no access to anyone. So
        // jobOwner and cluster administrator _only_ can do 'stuff'
        aclConfigured = " ";
      }
      acls.put(aclName, new AccessControlList(aclConfigured));
    }
    return acls;
  }

  /**
   * If authorization is enabled, checks whether the user (in the callerUGI)
   * is authorized to perform the operation specified by 'jobOperation' on
   * the job by checking if the user is jobOwner or part of job ACL for the
   * specific job operation.
   * <ul>
   * <li>The owner of the job can do any operation on the job</li>
   * <li>For all other users/groups job-acls are checked</li>
   * </ul>
   * @param callerUGI
   * @param jobOperation
   * @param jobOwner
   * @param jobACL
   * @throws AccessControlException
   */
  boolean checkAccess(UserGroupInformation callerUGI,
      JobACL jobOperation, String jobOwner, AccessControlList jobACL) {

    String user = callerUGI.getShortUserName();
    if (!areACLsEnabled()) {
      return true;
    }

    // Allow Job-owner for any operation on the job
    if (user.equals(jobOwner)
        || jobACL.isUserAllowed(callerUGI)) {
      return true;
    }

    return false;
  }
}
