package org.apache.hadoop.mapred.pipes;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * This partitioner is one that can either be set manually per a record or it
 * can fall back onto a Java partitioner that was set by the user.
 */
class PipesPartitioner<K extends WritableComparable,
                       V extends Writable>
  implements Partitioner<K, V> {
  
  private static ThreadLocal<Integer> cache = new ThreadLocal<Integer>();
  private Partitioner<K, V> part = null;
  
  @SuppressWarnings("unchecked")
  public void configure(JobConf conf) {
    part =
      ReflectionUtils.newInstance(Submitter.getJavaPartitioner(conf), conf);
  }

  /**
   * Set the next key to have the given partition.
   * @param newValue the next partition value
   */
  static void setNextPartition(int newValue) {
    cache.set(newValue);
  }

  /**
   * If a partition result was set manually, return it. Otherwise, we call
   * the Java partitioner.
   * @param key the key to partition
   * @param value the value to partition
   * @param numPartitions the number of reduces
   */
  public int getPartition(K key, V value, 
                          int numPartitions) {
    Integer result = cache.get();
    if (result == null) {
      return part.getPartition(key, value, numPartitions);
    } else {
      return result;
    }
  }

}
