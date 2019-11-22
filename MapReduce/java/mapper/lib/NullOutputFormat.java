package org.apache.hadoop.mapred.lib;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.util.Progressable;

/**
 * Consume all outputs and put them in /dev/null. 
 * @deprecated Use 
 *   {@link org.apache.hadoop.mapreduce.lib.output.NullOutputFormat} instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class NullOutputFormat<K, V> implements OutputFormat<K, V> {
  
  public RecordWriter<K, V> getRecordWriter(FileSystem ignored, JobConf job, 
                                      String name, Progressable progress) {
    return new RecordWriter<K, V>(){
        public void write(K key, V value) { }
        public void close(Reporter reporter) { }
      };
  }
  
  public void checkOutputSpecs(FileSystem ignored, JobConf job) { }
}
