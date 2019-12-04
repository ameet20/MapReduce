package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that maintain the smallest of 
 * a sequence of strings.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.StringValueMin} instead 
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class StringValueMin 
    extends org.apache.hadoop.mapreduce.lib.aggregate.StringValueMin 
    implements ValueAggregator<String> {
}
