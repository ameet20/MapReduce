package org.apache.hadoop.mapred.join;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;

/**
 * This defines an interface to a stateful Iterator that can replay elements
 * added to it directly.
 * Note that this does not extend {@link java.util.Iterator}.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.join.ResetableIterator} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface ResetableIterator<T extends Writable> 
    extends org.apache.hadoop.mapreduce.lib.join.ResetableIterator<T> {

  public static class EMPTY<U extends Writable>
      extends org.apache.hadoop.mapreduce.lib.join.ResetableIterator.EMPTY<U>
      implements ResetableIterator<U> {
  }
}
