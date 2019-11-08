package org.apache.hadoop.mapred.lib;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.util.Progressable;

/**
 * FilterOutputFormat is a convenience class that wraps OutputFormat. 
 * @deprecated Use 
 *   {@link org.apache.hadoop.mapreduce.lib.output.FilterOutputFormat} instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class FilterOutputFormat<K, V> implements OutputFormat<K, V> {

  protected OutputFormat<K,V> baseOut;

  public FilterOutputFormat () {
    this.baseOut = null;
  }

  /**
   * Create a FilterOutputFormat based on the supplied output format.
   * @param out the underlying OutputFormat
   */
  public FilterOutputFormat (OutputFormat<K,V> out) {
    this.baseOut = out;
  }

  public RecordWriter<K, V> getRecordWriter(FileSystem ignored, JobConf job, 
      String name, Progressable progress) throws IOException {
    return getBaseOut().getRecordWriter(ignored, job, name, progress);
  }

  public void checkOutputSpecs(FileSystem ignored, JobConf job) 
  throws IOException {
    getBaseOut().checkOutputSpecs(ignored, job);
  }
  
  private OutputFormat<K,V> getBaseOut() throws IOException {
    if (baseOut == null) {
      throw new IOException("Outputformat not set for FilterOutputFormat");
    }
    return baseOut;
  }

  /**
   * <code>FilterRecordWriter</code> is a convenience wrapper
   * class that implements  {@link RecordWriter}.
   */

  public static class FilterRecordWriter<K,V> implements RecordWriter<K,V> {

    protected RecordWriter<K,V> rawWriter = null;

    public FilterRecordWriter() throws IOException {
      rawWriter = null;
    }

    public FilterRecordWriter(RecordWriter<K,V> rawWriter)  throws IOException {
      this.rawWriter = rawWriter;
    }

    public void close(Reporter reporter) throws IOException {
      getRawWriter().close(reporter);
    }

    public void write(K key, V value) throws IOException {
      getRawWriter().write(key, value);
    }
    
    private RecordWriter<K,V> getRawWriter() throws IOException {
      if (rawWriter == null) {
        throw new IOException ("Record Writer not set for FilterRecordWriter");
      }
      return rawWriter;
    }
  }

}
