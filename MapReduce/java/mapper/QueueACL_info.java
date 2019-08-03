package org.apache.hadoop.mapred;

/**
 *  Class to encapsulate Queue ACLs for a particular
 *  user.
 * @deprecated Use {@link org.apache.hadoop.mapreduce.QueueAclsInfo} instead
 */
@Deprecated
class QueueAclsInfo extends org.apache.hadoop.mapreduce.QueueAclsInfo {

  /**
   * Default constructor for QueueAclsInfo.
   * 
   */
  QueueAclsInfo() {
    super();
  }

  /**
   * Construct a new QueueAclsInfo object using the queue name and the
   * queue operations array
   * 
   * @param queueName Name of the job queue
   * @param queue operations
   * 
   */
  QueueAclsInfo(String queueName, String[] operations) {
    super(queueName, operations);
  }
  
  public static QueueAclsInfo downgrade(
      org.apache.hadoop.mapreduce.QueueAclsInfo acl) {
    return new QueueAclsInfo(acl.getQueueName(), acl.getOperations());
  }
}
