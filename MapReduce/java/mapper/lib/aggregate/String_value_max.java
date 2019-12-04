package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that maintain the biggest of 
 * a sequence of strings.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.StringValueMax} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class StringValueMax 
    extends org.apache.hadoop.mapreduce.lib.aggregate.StringValueMax 
    implements ValueAggregator<String> {
}
