package org.apache.hadoop.mapred;

/**
 * Configuration Options used in the priority schedulers
 * -all in one place for ease of referencing in code.
 */
public class PrioritySchedulerOptions {
  /** {@value} */
  public static final String DYNAMIC_SCHEDULER_BUDGET_FILE = "mapred.dynamic-scheduler.budget-file";
  /** {@value} */
  public static final String DYNAMIC_SCHEDULER_STORE = "mapred.dynamic-scheduler.store";
  /** {@value} */
  public static final String MAPRED_QUEUE_NAMES = "mapred.queue.names";
  /** {@value} */
  public static final String DYNAMIC_SCHEDULER_SCHEDULER = "mapred.dynamic-scheduler.scheduler";
  /** {@value} */
  public static final String DYNAMIC_SCHEDULER_ALLOC_INTERVAL = "mapred.dynamic-scheduler.alloc-interval";
}
