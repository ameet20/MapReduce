package org.apache.hadoop.mapreduce.lib.input;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.SplittableCompressionCodec;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * An {@link InputFormat} for plain text files. Files are broken into lines.
 * Either line feed or carriage-return are used to signal end of line. 
 * Each line is divided into key and value parts by a separator byte. If no
 * such a byte exists, the key will be the entire line and value will be empty.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class KeyValueTextInputFormat extends FileInputFormat<Text, Text> {

  @Override
  protected boolean isSplitable(JobContext context, Path file) {
    final CompressionCodec codec =
      new CompressionCodecFactory(context.getConfiguration()).getCodec(file);
    if (null == codec) {
      return true;
    }
    return codec instanceof SplittableCompressionCodec;
  }

  public RecordReader<Text, Text> createRecordReader(InputSplit genericSplit,
      TaskAttemptContext context) throws IOException {
    
    context.setStatus(genericSplit.toString());
    return new KeyValueLineRecordReader(context.getConfiguration());
  }

}
