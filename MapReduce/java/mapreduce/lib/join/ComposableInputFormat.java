package org.apache.hadoop.mapreduce.lib.join;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * Refinement of InputFormat requiring implementors to provide
 * ComposableRecordReader instead of RecordReader.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class ComposableInputFormat<K extends WritableComparable<?>,
                                            V extends Writable>
    extends InputFormat<K,V> {

  public abstract ComposableRecordReader<K,V> createRecordReader(
    InputSplit split, TaskAttemptContext context) 
    throws IOException, InterruptedException;

}
