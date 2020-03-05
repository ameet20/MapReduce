package org.apache.hadoop.mapreduce.lib.aggregate;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;

/**
 * This interface defines the contract a value aggregator descriptor must
 * support. Such a descriptor can be configured with a {@link Configuration}
 * object. Its main function is to generate a list of aggregation-id/value 
 * pairs. An aggregation id encodes an aggregation type which is used to 
 * guide the way to aggregate the value in the reduce/combiner phrase of an
 * Aggregate based job. 
 * The mapper in an Aggregate based map/reduce job may create one or more of
 * ValueAggregatorDescriptor objects at configuration time. For each input
 * key/value pair, the mapper will use those objects to create aggregation
 * id/value pairs.
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface ValueAggregatorDescriptor {

  public static final String TYPE_SEPARATOR = ":";

  public static final Text ONE = new Text("1");

  /**
   * Generate a list of aggregation-id/value pairs for 
   * the given key/value pair.
   * This function is usually called by the mapper of an Aggregate based job.
   * 
   * @param key
   *          input key
   * @param val
   *          input value
   * @return a list of aggregation id/value pairs. An aggregation id encodes an
   *         aggregation type which is used to guide the way to aggregate the
   *         value in the reduce/combiner phrase of an Aggregate based job.
   */
  public ArrayList<Entry<Text, Text>> generateKeyValPairs(Object key,
                                                          Object val);

  /**
   * Configure the object
   * 
   * @param conf
   *          a Configuration object that may contain the information 
   *          that can be used to configure the object.
   */
  public void configure(Configuration conf);
}
