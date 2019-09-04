package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.util.Progressable;

/**
 * @deprecated Use {@link org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl}
 *   instead.
 */
@Deprecated
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class TaskAttemptContextImpl
       extends org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl 
       implements TaskAttemptContext {
  private Reporter reporter;

  TaskAttemptContextImpl(JobConf conf, TaskAttemptID taskid) {
    this(conf, taskid, Reporter.NULL);
  }
  
  TaskAttemptContextImpl(JobConf conf, TaskAttemptID taskid,
                         Reporter reporter) {
    super(conf, taskid);
    this.reporter = reporter;
  }
  
  /**
   * Get the taskAttemptID.
   *  
   * @return TaskAttemptID
   */
  public TaskAttemptID getTaskAttemptID() {
    return (TaskAttemptID) super.getTaskAttemptID();
  }
  
  public Progressable getProgressible() {
    return reporter;
  }
  
  public JobConf getJobConf() {
    return (JobConf) getConfiguration();
  }
  
  @Override
  public float getProgress() {
    return reporter.getProgress();
  }

  @Override
  public Counter getCounter(Enum<?> counterName) {
    return reporter.getCounter(counterName);
  }

  @Override
  public Counter getCounter(String groupName, String counterName) {
    return reporter.getCounter(groupName, counterName);
  }

  /**
   * Report progress.
   */
  @Override
  public void progress() {
    reporter.progress();
  }

  /**
   * Set the current status of the task to the given string.
   */
  @Override
  public void setStatus(String status) {
    setStatusString(status);
    reporter.setStatus(status);
  }


}
