package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that dedupes a sequence of objects.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.UniqValueCount} instead 
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class UniqValueCount 
    extends org.apache.hadoop.mapreduce.lib.aggregate.UniqValueCount 
    implements ValueAggregator<Object> {
  /**
   * the default constructor
   * 
   */
  public UniqValueCount() {
    super();
  }
  
  /**
   * constructor
   * @param maxNum the limit in the number of unique values to keep.
   *  
   */
  public UniqValueCount(long maxNum) {
    super(maxNum);
  }
}
