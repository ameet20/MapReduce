package org.apache.hadoop.mapred.lib;


import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;

/**
 * Partitioner effecting a total order by reading split points from
 * an externally generated source.
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner}
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class TotalOrderPartitioner<K extends WritableComparable<?>,V>
    extends org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner<K, V>
    implements Partitioner<K,V> {

  public TotalOrderPartitioner() { }

  public void configure(JobConf job) {
    super.setConf(job);
  }

}
