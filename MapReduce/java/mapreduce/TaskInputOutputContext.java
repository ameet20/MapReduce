package org.apache.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * A context object that allows input and output from the task. It is only
 * supplied to the {@link Mapper} or {@link Reducer}.
 * @param <KEYIN> the input key type for the task
 * @param <VALUEIN> the input value type for the task
 * @param <KEYOUT> the output key type for the task
 * @param <VALUEOUT> the output value type for the task
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public interface TaskInputOutputContext<KEYIN,VALUEIN,KEYOUT,VALUEOUT> 
       extends TaskAttemptContext {

  /**
   * Advance to the next key, value pair, returning null if at end.
   * @return the key object that was read into, or null if no more
   */
  public boolean nextKeyValue() throws IOException, InterruptedException;
 
  /**
   * Get the current key.
   * @return the current key object or null if there isn't one
   * @throws IOException
   * @throws InterruptedException
   */
  public KEYIN getCurrentKey() throws IOException, InterruptedException;

  /**
   * Get the current value.
   * @return the value object that was read into
   * @throws IOException
   * @throws InterruptedException
   */
  public VALUEIN getCurrentValue() throws IOException, InterruptedException;

  /**
   * Generate an output key/value pair.
   */
  public void write(KEYOUT key, VALUEOUT value) 
      throws IOException, InterruptedException;

  /**
   * Get the {@link OutputCommitter} for the task-attempt.
   * @return the <code>OutputCommitter</code> for the task-attempt
   */
  public OutputCommitter getOutputCommitter();
}
