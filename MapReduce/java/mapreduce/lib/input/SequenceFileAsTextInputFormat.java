package org.apache.hadoop.mapreduce.lib.input;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * This class is similar to SequenceFileInputFormat, except it generates
 * SequenceFileAsTextRecordReader which converts the input keys and values
 * to their String forms by calling toString() method. 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class SequenceFileAsTextInputFormat
  extends SequenceFileInputFormat<Text, Text> {

  public SequenceFileAsTextInputFormat() {
    super();
  }

  public RecordReader<Text, Text> createRecordReader(InputSplit split,
      TaskAttemptContext context) throws IOException {
    context.setStatus(split.toString());
    return new SequenceFileAsTextRecordReader();
  }
}
