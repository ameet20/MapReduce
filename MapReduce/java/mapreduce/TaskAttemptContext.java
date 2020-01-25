package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.util.Progressable;

/**
 * The context for task attempts.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public interface TaskAttemptContext extends JobContext, Progressable {

  /**
   * Get the unique name for this task attempt.
   */
  public TaskAttemptID getTaskAttemptID();

  /**
   * Set the current status of the task to the given string.
   */
  public void setStatus(String msg);

  /**
   * Get the last set status message.
   * @return the current status message
   */
  public String getStatus();
  
  /**
   * The current progress of the task attempt.
   * @return a number between 0.0 and 1.0 (inclusive) indicating the attempt's
   * progress.
   */
  public abstract float getProgress();

  /**
   * Get the {@link Counter} for the given <code>counterName</code>.
   * @param counterName counter name
   * @return the <code>Counter</code> for the given <code>counterName</code>
   */
  public Counter getCounter(Enum<?> counterName);

  /**
   * Get the {@link Counter} for the given <code>groupName</code> and 
   * <code>counterName</code>.
   * @param counterName counter name
   * @return the <code>Counter</code> for the given <code>groupName</code> and 
   *         <code>counterName</code>
   */
  public Counter getCounter(String groupName, String counterName);

}
