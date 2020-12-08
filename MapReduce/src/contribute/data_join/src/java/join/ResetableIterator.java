package org.apache.hadoop.contrib.utils.join;

import java.io.IOException;
import java.util.Iterator;

/**
 * This defines an iterator interface that will help the reducer class
 * re-group its input by source tags. Once the values are re-grouped,
 * the reducer will receive the cross product of values from different groups.
 */
public interface ResetableIterator extends Iterator {
  public void reset();

  public void add(Object item);

  public void close() throws IOException;
}
