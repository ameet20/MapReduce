package org.apache.hadoop.mapred.lib;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.mapred.JobConf;

/** Partition keys by their {@link Object#hashCode()}. 
 * @deprecated Use 
 *   {@link org.apache.hadoop.mapreduce.lib.partition.HashPartitioner} instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class HashPartitioner<K2, V2> implements Partitioner<K2, V2> {

  public void configure(JobConf job) {}

  /** Use {@link Object#hashCode()} to partition. */
  public int getPartition(K2 key, V2 value,
                          int numReduceTasks) {
    return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
  }

}
