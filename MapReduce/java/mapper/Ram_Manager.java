package org.apache.hadoop.mapred;

import java.io.InputStream;

/**
 * <code>RamManager</code> manages a memory pool of a configured limit.
 */
interface RamManager {
  /**
   * Reserve memory for data coming through the given input-stream.
   * 
   * @param requestedSize size of memory requested
   * @param in input stream
   * @throws InterruptedException
   * @return <code>true</code> if memory was allocated immediately, 
   *         else <code>false</code>
   */
  boolean reserve(int requestedSize, InputStream in) 
  throws InterruptedException;
  
  /**
   * Return memory to the pool.
   * 
   * @param requestedSize size of memory returned to the pool
   */
  void unreserve(int requestedSize);
}
