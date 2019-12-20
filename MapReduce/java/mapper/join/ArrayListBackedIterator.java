package org.apache.hadoop.mapred.join;

import java.util.ArrayList;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;

/**
 * This class provides an implementation of ResetableIterator. The
 * implementation uses an {@link java.util.ArrayList} to store elements
 * added to it, replaying them as requested.
 * Prefer {@link StreamBackedIterator}.
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.join.ArrayListBackedIterator} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class ArrayListBackedIterator<X extends Writable> extends 
    org.apache.hadoop.mapreduce.lib.join.ArrayListBackedIterator<X>
    implements ResetableIterator<X> {

  public ArrayListBackedIterator() {
    super();
  }

  public ArrayListBackedIterator(ArrayList<X> data) {
    super(data);
  }
}
