package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/** That what may be configured. */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface JobConfigurable {
  /** Initializes a new instance from a {@link JobConf}.
   *
   * @param job the configuration
   */
  void configure(JobConf job);
}
