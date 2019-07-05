package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.util.Progressable;

/**
 * @deprecated Use {@link org.apache.hadoop.mapreduce.JobContext} instead.
 */
@Deprecated
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class JobContextImpl 
    extends org.apache.hadoop.mapreduce.task.JobContextImpl 
    implements JobContext {
  private JobConf job;
  private Progressable progress;

  JobContextImpl(JobConf conf, org.apache.hadoop.mapreduce.JobID jobId, 
                 Progressable progress) {
    super(conf, jobId);
    this.job = conf;
    this.progress = progress;
  }

  JobContextImpl(JobConf conf, org.apache.hadoop.mapreduce.JobID jobId) {
    this(conf, jobId, Reporter.NULL);
  }
  
  /**
   * Get the job Configuration
   * 
   * @return JobConf
   */
  public JobConf getJobConf() {
    return job;
  }
  
  /**
   * Get the progress mechanism for reporting progress.
   * 
   * @return progress mechanism 
   */
  public Progressable getProgressible() {
    return progress;
  }
}
