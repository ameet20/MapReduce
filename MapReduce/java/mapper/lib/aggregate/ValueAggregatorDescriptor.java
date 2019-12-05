package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;

/**
 * This interface defines the contract a value aggregator descriptor must
 * support. Such a descriptor can be configured with a JobConf object. Its main
 * function is to generate a list of aggregation-id/value pairs. An aggregation
 * id encodes an aggregation type which is used to guide the way to aggregate
 * the value in the reduce/combiner phrase of an Aggregate based job.The mapper in
 * an Aggregate based map/reduce job may create one or more of
 * ValueAggregatorDescriptor objects at configuration time. For each input
 * key/value pair, the mapper will use those objects to create aggregation
 * id/value pairs.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorDescriptor}
 * instead 
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface ValueAggregatorDescriptor extends 
    org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorDescriptor {

  public static final String TYPE_SEPARATOR = org.apache.hadoop.mapreduce.
      lib.aggregate.ValueAggregatorDescriptor.TYPE_SEPARATOR;

  public static final Text ONE = org.apache.hadoop.mapreduce.
      lib.aggregate.ValueAggregatorDescriptor.ONE;

  /**
   * Configure the object
   * 
   * @param job
   *          a JobConf object that may contain the information that can be used
   *          to configure the object.
   */
  public void configure(JobConf job);
}
