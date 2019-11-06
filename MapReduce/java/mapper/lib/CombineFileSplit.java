package org.apache.hadoop.mapred.lib;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;

/**
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.input.CombineFileSplit}
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class CombineFileSplit extends 
    org.apache.hadoop.mapreduce.lib.input.CombineFileSplit 
    implements InputSplit {

  private JobConf job;

  public CombineFileSplit() {
  }
  
  public CombineFileSplit(JobConf job, Path[] files, long[] start, 
          long[] lengths, String[] locations) {
    super(files, start, lengths, locations);
    this.job = job;
  }

  public CombineFileSplit(JobConf job, Path[] files, long[] lengths) {
    super(files, lengths);
    this.job = job;
  }
  
  /**
   * Copy constructor
   */
  public CombineFileSplit(CombineFileSplit old) throws IOException {
    super(old);
  }

  public JobConf getJob() {
    return job;
  }
}
