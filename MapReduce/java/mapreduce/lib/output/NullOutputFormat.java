package org.apache.hadoop.mapreduce.lib.output;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * Consume all outputs and put them in /dev/null. 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class NullOutputFormat<K, V> extends OutputFormat<K, V> {
  
  @Override
  public RecordWriter<K, V> 
         getRecordWriter(TaskAttemptContext context) {
    return new RecordWriter<K, V>(){
        public void write(K key, V value) { }
        public void close(TaskAttemptContext context) { }
      };
  }
  
  @Override
  public void checkOutputSpecs(JobContext context) { }
  
  @Override
  public OutputCommitter getOutputCommitter(TaskAttemptContext context) {
    return new OutputCommitter() {
      public void abortTask(TaskAttemptContext taskContext) { }
      public void cleanupJob(JobContext jobContext) { }
      public void commitTask(TaskAttemptContext taskContext) { }
      public boolean needsTaskCommit(TaskAttemptContext taskContext) {
        return false;
      }
      public void setupJob(JobContext jobContext) { }
      public void setupTask(TaskAttemptContext taskContext) { }
    };
  }
}
