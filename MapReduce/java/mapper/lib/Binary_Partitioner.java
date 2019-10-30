package org.apache.hadoop.mapred.lib;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.BinaryComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;

/**
 * Partition {@link BinaryComparable} keys using a configurable part of 
 * the bytes array returned by {@link BinaryComparable#getBytes()}. 
 * 
 * @see org.apache.hadoop.mapreduce.lib.partition.BinaryPartitioner
 * @deprecated Use
 *   {@link org.apache.hadoop.mapreduce.lib.partition.BinaryPartitioner}
 *   instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class BinaryPartitioner<V>  
  extends org.apache.hadoop.mapreduce.lib.partition.BinaryPartitioner<V>
  implements Partitioner<BinaryComparable, V> {
  
  public void configure(JobConf job) {
    super.setConf(job);
  }
  
}
