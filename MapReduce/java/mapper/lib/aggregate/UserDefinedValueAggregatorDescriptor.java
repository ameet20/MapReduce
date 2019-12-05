package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapred.JobConf;

/**
 * This class implements a wrapper for a user defined value aggregator descriptor.
 * It servs two functions: One is to create an object of ValueAggregatorDescriptor from the
 * name of a user defined class that may be dynamically loaded. The other is to
 * deligate inviokations of generateKeyValPairs function to the created object.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.UserDefinedValueAggregatorDescriptor}
 * instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class UserDefinedValueAggregatorDescriptor extends org.apache.hadoop.
    mapreduce.lib.aggregate.UserDefinedValueAggregatorDescriptor
    implements ValueAggregatorDescriptor {

  /**
   * Create an instance of the given class
   * @param className the name of the class
   * @return a dynamically created instance of the given class 
   */
  public static Object createInstance(String className) {
    return org.apache.hadoop.mapreduce.lib.aggregate.
      UserDefinedValueAggregatorDescriptor.createInstance(className);
  }

  /**
   * 
   * @param className the class name of the user defined descriptor class
   * @param job a configure object used for decriptor configuration
   */
  public UserDefinedValueAggregatorDescriptor(String className, JobConf job) {
    super(className, job);
    ((ValueAggregatorDescriptor)theAggregatorDescriptor).configure(job);
  }

  /**
   *  Do nothing.
   */
  public void configure(JobConf job) {

  }

}
