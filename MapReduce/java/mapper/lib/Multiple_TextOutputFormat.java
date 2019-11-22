package org.apache.hadoop.mapred.lib;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Progressable;

/**
 * This class extends the MultipleOutputFormat, allowing to write the output
 * data to different output files in Text output format.
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.output.MultipleOutputs} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class MultipleTextOutputFormat<K, V>
    extends MultipleOutputFormat<K, V> {

  private TextOutputFormat<K, V> theTextOutputFormat = null;

  @Override
  protected RecordWriter<K, V> getBaseRecordWriter(FileSystem fs, JobConf job,
      String name, Progressable arg3) throws IOException {
    if (theTextOutputFormat == null) {
      theTextOutputFormat = new TextOutputFormat<K, V>();
    }
    return theTextOutputFormat.getRecordWriter(fs, job, name, arg3);
  }
}
