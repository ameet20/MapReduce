package org.apache.hadoop.mapreduce;

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
public abstract class RecordWriter<K, V> {
  /** 
   * Writes a key/value pair.
   *
   * @param key the key to write.
   * @param value the value to write.
   * @throws IOException
   */      
  public abstract void write(K key, V value
                             ) throws IOException, InterruptedException;

  /** 
   * Close this <code>RecordWriter</code> to future operations.
   * 
   * @param context the context of the task
   * @throws IOException
   */ 
  public abstract void close(TaskAttemptContext context
                             ) throws IOException, InterruptedException;
}
