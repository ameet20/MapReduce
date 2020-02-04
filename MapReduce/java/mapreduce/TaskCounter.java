package org.apache.hadoop.mapreduce;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

// Counters used by Task classes
@InterfaceAudience.Public
@InterfaceStability.Evolving
public enum TaskCounter {
  MAP_INPUT_RECORDS, 
  MAP_OUTPUT_RECORDS,
  MAP_SKIPPED_RECORDS,
  MAP_OUTPUT_BYTES,
  SPLIT_RAW_BYTES,
  COMBINE_INPUT_RECORDS,
  COMBINE_OUTPUT_RECORDS,
  REDUCE_INPUT_GROUPS,
  REDUCE_SHUFFLE_BYTES,
  REDUCE_INPUT_RECORDS,
  REDUCE_OUTPUT_RECORDS,
  REDUCE_SKIPPED_GROUPS,
  REDUCE_SKIPPED_RECORDS,
  SPILLED_RECORDS,
  SHUFFLED_MAPS, 
  FAILED_SHUFFLE,
  MERGED_MAP_OUTPUTS,
  GC_TIME_MILLIS,
  CPU_MILLISECONDS,
  PHYSICAL_MEMORY_BYTES,
  VIRTUAL_MEMORY_BYTES,
  COMMITTED_HEAP_BYTES
}
