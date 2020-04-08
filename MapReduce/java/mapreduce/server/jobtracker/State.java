package org.apache.hadoop.mapreduce.server.jobtracker;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.Cluster.JobTrackerStatus;

/**
 * Describes the state of JobTracker
 * @deprecated Use {@link JobTrackerStatus} instead.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
@Deprecated
public enum State {
  INITIALIZING, RUNNING;
}
