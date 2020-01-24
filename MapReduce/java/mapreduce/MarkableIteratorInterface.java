package org.apache.hadoop.mapreduce;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * <code>MarkableIteratorInterface</code> is an interface for a iterator that 
 * supports mark-reset functionality. 
 *
 * <p>Mark can be called at any point during the iteration process and a reset
 * will go back to the last record before the call to the previous mark.
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
interface MarkableIteratorInterface<VALUE> extends Iterator<VALUE> {
  /**
   * Mark the current record. A subsequent call to reset will rewind
   * the iterator to this record.
   * @throws IOException
   */
  void mark() throws IOException;
  
  /**
   * Reset the iterator to the last record before a call to the previous mark
   * @throws IOException
   */
  void reset() throws IOException;
  
  /**
   * Clear any previously set mark
   * @throws IOException
   */
  void clearMark() throws IOException;
}
