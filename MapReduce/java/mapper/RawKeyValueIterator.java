package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.util.Progress;

/**
 * <code>RawKeyValueIterator</code> is an iterator used to iterate over
 * the raw keys and values during sort/merge of intermediate data. 
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public interface RawKeyValueIterator {
  /** 
   * Gets the current raw key.
   * 
   * @return Gets the current raw key as a DataInputBuffer
   * @throws IOException
   */
  DataInputBuffer getKey() throws IOException;
  
  /** 
   * Gets the current raw value.
   * 
   * @return Gets the current raw value as a DataInputBuffer 
   * @throws IOException
   */
  DataInputBuffer getValue() throws IOException;
  
  /** 
   * Sets up the current key and value (for getKey and getValue).
   * 
   * @return <code>true</code> if there exists a key/value, 
   *         <code>false</code> otherwise. 
   * @throws IOException
   */
  boolean next() throws IOException;
  
  /** 
   * Closes the iterator so that the underlying streams can be closed.
   * 
   * @throws IOException
   */
  void close() throws IOException;
  
  /** Gets the Progress object; this has a float (0.0 - 1.0) 
   * indicating the bytes processed by the iterator so far
   */
  Progress getProgress();
}
