package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Enum for map, reduce, job-setup, job-cleanup, task-cleanup task types.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public enum TaskType {
  MAP, REDUCE, JOB_SETUP, JOB_CLEANUP, TASK_CLEANUP
}
