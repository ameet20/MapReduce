package org.apache.hadoop.mapred.join;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;

/**
 * This class provides an implementation of ResetableIterator. This
 * implementation uses a byte array to store elements added to it.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.join.StreamBackedIterator} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class StreamBackedIterator<X extends Writable>
    extends org.apache.hadoop.mapreduce.lib.join.StreamBackedIterator<X>
    implements ResetableIterator<X> {
}
