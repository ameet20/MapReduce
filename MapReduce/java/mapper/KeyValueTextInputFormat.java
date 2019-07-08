package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.SplittableCompressionCodec;

/**
 * An {@link InputFormat} for plain text files. Files are broken into lines.
 * Either linefeed or carriage-return are used to signal end of line. Each line
 * is divided into key and value parts by a separator byte. If no such a byte
 * exists, the key will be the entire line and value will be empty.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat} 
 * instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class KeyValueTextInputFormat extends FileInputFormat<Text, Text>
  implements JobConfigurable {

  private CompressionCodecFactory compressionCodecs = null;
  
  public void configure(JobConf conf) {
    compressionCodecs = new CompressionCodecFactory(conf);
  }
  
  protected boolean isSplitable(FileSystem fs, Path file) {
    final CompressionCodec codec = compressionCodecs.getCodec(file);
    if (null == codec) {
      return true;
    }
    return codec instanceof SplittableCompressionCodec;
  }
  
  public RecordReader<Text, Text> getRecordReader(InputSplit genericSplit,
                                                  JobConf job,
                                                  Reporter reporter)
    throws IOException {
    
    reporter.setStatus(genericSplit.toString());
    return new KeyValueLineRecordReader(job, (FileSplit) genericSplit);
  }

}
