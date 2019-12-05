package org.apache.hadoop.mapred.lib.aggregate;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This interface defines the minimal protocol for value aggregators.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregator} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface ValueAggregator<E> extends 
    org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregator<E> {
}
