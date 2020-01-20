package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

// Per-job counters
@InterfaceAudience.Public
@InterfaceStability.Evolving
public enum JobCounter {
  NUM_FAILED_MAPS, 
  NUM_FAILED_REDUCES,
  TOTAL_LAUNCHED_MAPS,
  TOTAL_LAUNCHED_REDUCES,
  OTHER_LOCAL_MAPS,
  DATA_LOCAL_MAPS,
  RACK_LOCAL_MAPS,
  SLOTS_MILLIS_MAPS,
  SLOTS_MILLIS_REDUCES,
  FALLOW_SLOTS_MILLIS_MAPS,
  FALLOW_SLOTS_MILLIS_REDUCES
}
