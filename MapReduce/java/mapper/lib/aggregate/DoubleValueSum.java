package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that sums up a sequence of double
 * values.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.DoubleValueSum} instead 
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class DoubleValueSum 
    extends org.apache.hadoop.mapreduce.lib.aggregate.DoubleValueSum
    implements ValueAggregator<String> {

}
