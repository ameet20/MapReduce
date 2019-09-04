package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.util.Progressable;

/**
 * @deprecated Use {@link org.apache.hadoop.mapreduce.TaskAttemptContext}
 *   instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface TaskAttemptContext 
       extends org.apache.hadoop.mapreduce.TaskAttemptContext {

  public TaskAttemptID getTaskAttemptID();

  public Progressable getProgressible();
  
  public JobConf getJobConf();
}
