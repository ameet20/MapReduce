package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that computes the 
 * histogram of a sequence of strings.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.ValueHistogram} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class ValueHistogram 
    extends org.apache.hadoop.mapreduce.lib.aggregate.ValueHistogram 
    implements ValueAggregator<String> {
}
