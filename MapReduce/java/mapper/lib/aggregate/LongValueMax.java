package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;


/**
 * This class implements a value aggregator that maintain the maximum of 
 * a sequence of long values.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.LongValueMax} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class LongValueMax 
    extends org.apache.hadoop.mapreduce.lib.aggregate.LongValueMax
    implements ValueAggregator<String> {
}
