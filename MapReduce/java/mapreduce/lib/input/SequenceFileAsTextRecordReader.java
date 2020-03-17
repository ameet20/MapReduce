package org.apache.hadoop.mapreduce.lib.input;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * This class converts the input keys and values to their String forms by
 * calling toString() method. This class to SequenceFileAsTextInputFormat
 * class is as LineRecordReader class to TextInputFormat class.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class SequenceFileAsTextRecordReader
  extends RecordReader<Text, Text> {
  
  private final SequenceFileRecordReader<WritableComparable<?>, Writable>
    sequenceFileRecordReader;

  private Text key;
  private Text value;

  public SequenceFileAsTextRecordReader()
    throws IOException {
    sequenceFileRecordReader =
      new SequenceFileRecordReader<WritableComparable<?>, Writable>();
  }

  public void initialize(InputSplit split, TaskAttemptContext context)
      throws IOException, InterruptedException {
    sequenceFileRecordReader.initialize(split, context);
  }

  @Override
  public Text getCurrentKey() 
      throws IOException, InterruptedException {
    return key;
  }
  
  @Override
  public Text getCurrentValue() 
      throws IOException, InterruptedException {
    return value;
  }
  
  /** Read key/value pair in a line. */
  public synchronized boolean nextKeyValue() 
      throws IOException, InterruptedException {
    if (!sequenceFileRecordReader.nextKeyValue()) {
      return false;
    }
    if (key == null) {
      key = new Text(); 
    }
    if (value == null) {
      value = new Text(); 
    }
    key.set(sequenceFileRecordReader.getCurrentKey().toString());
    value.set(sequenceFileRecordReader.getCurrentValue().toString());
    return true;
  }
  
  public float getProgress() throws IOException,  InterruptedException {
    return sequenceFileRecordReader.getProgress();
  }
  
  public synchronized void close() throws IOException {
    sequenceFileRecordReader.close();
  }
}
