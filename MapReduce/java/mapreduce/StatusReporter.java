package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience;

@InterfaceAudience.Private
public abstract class StatusReporter {
  public abstract Counter getCounter(Enum<?> name);
  public abstract Counter getCounter(String group, String name);
  public abstract void progress();
  /**
   * Get the current progress.
   * @return a number between 0.0 and 1.0 (inclusive) indicating the attempt's 
   * progress.
   */
  public abstract float getProgress();
  public abstract void setStatus(String status);
}
