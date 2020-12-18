package org.apache.hadoop.mapred;

/** 
 * This class allows the scheduler to retrieve periodic
 * queue allocation info from the queue share manager.
 */ 
abstract public class QueueTaskScheduler extends TaskScheduler {
  /**
   * Sets the queue share manager of a scheduler
   * @param allocator the queue share manager of this scheduler
   */
  public abstract void setAllocator(QueueAllocator allocator);
}
