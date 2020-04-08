package org.apache.hadoop.mapreduce.task.reduce;

/**
 * An interface for reporting exceptions to other threads
 */
interface ExceptionReporter {
  void reportException(Throwable t);
}
