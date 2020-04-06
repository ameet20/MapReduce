package org.apache.hadoop.mapreduce.lib.output;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * FilterOutputFormat is a convenience class that wraps OutputFormat. 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class FilterOutputFormat <K,V> extends OutputFormat<K, V> {

  protected OutputFormat<K,V> baseOut;

  public FilterOutputFormat() {
    this.baseOut = null;
  }
  
  /**
   * Create a FilterOutputFormat based on the underlying output format.
   * @param baseOut the underlying OutputFormat
   */
  public FilterOutputFormat(OutputFormat<K,V> baseOut) {
    this.baseOut = baseOut;
  }

  @Override
  public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context) 
  throws IOException, InterruptedException {
    return getBaseOut().getRecordWriter(context);
  }

  @Override
  public void checkOutputSpecs(JobContext context) 
  throws IOException, InterruptedException {
    getBaseOut().checkOutputSpecs(context);
  }

  @Override
  public OutputCommitter getOutputCommitter(TaskAttemptContext context) 
  throws IOException, InterruptedException {
    return getBaseOut().getOutputCommitter(context);
  }

  private OutputFormat<K,V> getBaseOut() throws IOException {
    if (baseOut == null) {
      throw new IOException("OutputFormat not set for FilterOutputFormat");
    }
    return baseOut;
  }
  /**
   * <code>FilterRecordWriter</code> is a convenience wrapper
   * class that extends the {@link RecordWriter}.
   */

  public static class FilterRecordWriter<K,V> extends RecordWriter<K,V> {

    protected RecordWriter<K,V> rawWriter = null;

    public FilterRecordWriter() {
      rawWriter = null;
    }
    
    public FilterRecordWriter(RecordWriter<K,V> rwriter) {
      this.rawWriter = rwriter;
    }
    
    @Override
    public void write(K key, V value) throws IOException, InterruptedException {
      getRawWriter().write(key, value);
    }

    @Override
    public void close(TaskAttemptContext context) 
    throws IOException, InterruptedException {
      getRawWriter().close(context);
    }
    
    private RecordWriter<K,V> getRawWriter() throws IOException {
      if (rawWriter == null) {
        throw new IOException("Record Writer not set for FilterRecordWriter");
      }
      return rawWriter;
    }
  }
}
