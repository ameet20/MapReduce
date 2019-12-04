package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that sums up 
 * a sequence of long values.
 * 
 *@deprecated Use 
 *{@link org.apache.hadoop.mapreduce.lib.aggregate.LongValueSum} instead 
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class LongValueSum 
    extends org.apache.hadoop.mapreduce.lib.aggregate.LongValueSum 
    implements ValueAggregator<String> {
}
