package org.apache.hadoop.mapreduce.lib.input;

import java.io.IOException;


import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.MapContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/** An {@link RecordReader} for {@link SequenceFile}s. */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class SequenceFileRecordReader<K, V> extends RecordReader<K, V> {
  private SequenceFile.Reader in;
  private long start;
  private long end;
  private boolean more = true;
  private K key = null;
  private V value = null;
  protected Configuration conf;
  private Counter inputByteCounter;
  private long pos;
  
  @Override
  public void initialize(InputSplit split, 
                         TaskAttemptContext context
                         ) throws IOException, InterruptedException {
    FileSplit fileSplit = (FileSplit) split;
    inputByteCounter = ((MapContext)context).getCounter(
      FileInputFormat.COUNTER_GROUP, FileInputFormat.BYTES_READ);
    conf = context.getConfiguration();    
    Path path = fileSplit.getPath();
    FileSystem fs = path.getFileSystem(conf);
    this.in = new SequenceFile.Reader(fs, path, conf);
    this.end = fileSplit.getStart() + fileSplit.getLength();

    if (fileSplit.getStart() > in.getPosition()) {
      in.sync(fileSplit.getStart());                  // sync to start
    }

    this.start = in.getPosition();
    more = start < end;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean nextKeyValue() throws IOException, InterruptedException {
    if (!more) {
      return false;
    }
    inputByteCounter.increment(in.getPosition()-pos);
    pos = in.getPosition();
    key = (K) in.next(key);
    if (key == null || (pos >= end && in.syncSeen())) {
      more = false;
      key = null;
      value = null;
    } else {
      value = (V) in.getCurrentValue(value);
    }
    return more;
  }

  @Override
  public K getCurrentKey() {
    return key;
  }
  
  @Override
  public V getCurrentValue() {
    return value;
  }
  
  /**
   * Return the progress within the input split
   * @return 0.0 to 1.0 of the input byte range
   */
  public float getProgress() throws IOException {
    if (end == start) {
      return 0.0f;
    } else {
      return Math.min(1.0f, (in.getPosition() - start) / (float)(end - start));
    }
  }
  
  public synchronized void close() throws IOException { in.close(); }
  
}
