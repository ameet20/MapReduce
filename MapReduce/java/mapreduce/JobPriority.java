package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Used to describe the priority of the running job. 
 *
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public enum JobPriority {
  VERY_HIGH,
  HIGH,
  NORMAL,
  LOW,
  VERY_LOW;
}
