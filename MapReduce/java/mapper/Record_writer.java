package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.FileSystem;

/**
 * <code>RecordWriter</code> writes the output &lt;key, value&gt; pairs 
 * to an output file.
 
 * <p><code>RecordWriter</code> implementations write the job outputs to the
 * {@link FileSystem}.
 * 
 * @see OutputFormat
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface RecordWriter<K, V> {
  /** 
   * Writes a key/value pair.
   *
   * @param key the key to write.
   * @param value the value to write.
   * @throws IOException
   */      
  void write(K key, V value) throws IOException;

  /** 
   * Close this <code>RecordWriter</code> to future operations.
   * 
   * @param reporter facility to report progress.
   * @throws IOException
   */ 
  void close(Reporter reporter) throws IOException;
}
