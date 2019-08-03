package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/** 
 * Partitions the key space.
 * 
 * <p><code>Partitioner</code> controls the partitioning of the keys of the 
 * intermediate map-outputs. The key (or a subset of the key) is used to derive
 * the partition, typically by a hash function. The total number of partitions
 * is the same as the number of reduce tasks for the job. Hence this controls
 * which of the <code>m</code> reduce tasks the intermediate key (and hence the 
 * record) is sent for reduction.</p>
 * 
 * @see Reducer
 * @deprecated Use {@link org.apache.hadoop.mapreduce.Partitioner} instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface Partitioner<K2, V2> extends JobConfigurable {
  
  /** 
   * Get the paritition number for a given key (hence record) given the total 
   * number of partitions i.e. number of reduce-tasks for the job.
   *   
   * <p>Typically a hash function on a all or a subset of the key.</p>
   *
   * @param key the key to be paritioned.
   * @param value the entry value.
   * @param numPartitions the total number of partitions.
   * @return the partition number for the <code>key</code>.
   */
  int getPartition(K2 key, V2 value, int numPartitions);
}
