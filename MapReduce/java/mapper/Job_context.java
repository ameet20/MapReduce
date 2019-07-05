package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.util.Progressable;

/**
 * @deprecated Use {@link org.apache.hadoop.mapreduce.JobContext} instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface JobContext extends org.apache.hadoop.mapreduce.JobContext {
  /**
   * Get the job Configuration
   * 
   * @return JobConf
   */
  public JobConf getJobConf();
  
  /**
   * Get the progress mechanism for reporting progress.
   * 
   * @return progress mechanism 
   */
  public Progressable getProgressible();
}
