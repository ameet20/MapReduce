package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;

/**
 * This class is similar to SequenceFileInputFormat, 
 * except it generates SequenceFileAsTextRecordReader 
 * which converts the input keys and values to their 
 * String forms by calling toString() method.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.input.SequenceFileAsTextInputFormat}
 * instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class SequenceFileAsTextInputFormat
  extends SequenceFileInputFormat<Text, Text> {

  public SequenceFileAsTextInputFormat() {
    super();
  }

  public RecordReader<Text, Text> getRecordReader(InputSplit split,
                                                  JobConf job,
                                                  Reporter reporter)
    throws IOException {

    reporter.setStatus(split.toString());

    return new SequenceFileAsTextRecordReader(job, (FileSplit) split);
  }
}
