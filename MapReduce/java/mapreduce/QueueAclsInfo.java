package org.apache.hadoop.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;

/**
 *  Class to encapsulate Queue ACLs for a particular
 *  user.
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class QueueAclsInfo implements Writable {

  private String queueName;
  private String[] operations;
  /**
   * Default constructor for QueueAclsInfo.
   * 
   */
  public QueueAclsInfo() {
    
  }

  /**
   * Construct a new QueueAclsInfo object using the queue name and the
   * queue operations array
   * 
   * @param queueName Name of the job queue
   * @param operations
   */
  public QueueAclsInfo(String queueName, String[] operations) {
    this.queueName = queueName;
    this.operations = operations;    
  }

  /**
   * Get queue name.
   * 
   * @return name
   */
  public String getQueueName() {
    return queueName;
  }

  protected void setQueueName(String queueName) {
    this.queueName = queueName;
  }

  /**
   * Get opearations allowed on queue.
   * 
   * @return array of String
   */
  public String[] getOperations() {
    return operations;
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    queueName = Text.readString(in);
    operations = WritableUtils.readStringArray(in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    Text.writeString(out, queueName);
    WritableUtils.writeStringArray(out, operations);
  }
}
