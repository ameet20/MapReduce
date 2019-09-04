package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/** The states of a {@link TaskInProgress} as seen by the JobTracker.
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public enum TIPStatus {
  PENDING, RUNNING, COMPLETE, KILLED, FAILED;
}
