package org.apache.hadoop.mapred.lib;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;

/**
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.partition.InputSampler}
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class InputSampler<K,V> extends 
  org.apache.hadoop.mapreduce.lib.partition.InputSampler<K, V> {

  public InputSampler(JobConf conf) {
    super(conf);
  }

  public static <K,V> void writePartitionFile(JobConf job, Sampler<K,V> sampler)
      throws IOException, ClassNotFoundException, InterruptedException {
    writePartitionFile(new Job(job), sampler);
  }
}
