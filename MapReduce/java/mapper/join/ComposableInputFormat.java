package org.apache.hadoop.mapred.join;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Reporter;

/**
 * Refinement of InputFormat requiring implementors to provide
 * ComposableRecordReader instead of RecordReader.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.join.ComposableInputFormat} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface ComposableInputFormat<K extends WritableComparable,
                                       V extends Writable>
    extends InputFormat<K,V> {

  ComposableRecordReader<K,V> getRecordReader(InputSplit split,
      JobConf job, Reporter reporter) throws IOException;
}
