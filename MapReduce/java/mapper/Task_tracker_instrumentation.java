package org.apache.hadoop.mapred;

import java.io.File;

/**
 * TaskTrackerInstrumentation defines a number of instrumentation points
 * associated with TaskTrackers.  By default, the instrumentation points do
 * nothing, but subclasses can do arbitrary instrumentation and monitoring at
 * these points.
 * 
 * TaskTrackerInstrumentation interfaces are associated uniquely with a
 * TaskTracker.  We don't want an inner class here, because then subclasses
 * wouldn't have direct access to the associated TaskTracker.
 *  
 **/
class TaskTrackerInstrumentation  {

  protected final TaskTracker tt;
  
  public TaskTrackerInstrumentation(TaskTracker t) {
    tt = t;
  }
  
  /**
   * invoked when task attempt t succeeds
   * @param t
   */
  public void completeTask(TaskAttemptID t) { }
  
  public void timedoutTask(TaskAttemptID t) { }
  
  public void taskFailedPing(TaskAttemptID t) { }

  /**
   * Called just before task attempt t starts.
   * @param stdout the file containing standard out of the new task
   * @param stderr the file containing standard error of the new task 
   */
  public void reportTaskLaunch(TaskAttemptID t, File stdout, File stderr)  { }
  
  /**
   * called when task t has just finished.
   * @param t
   */
  public void reportTaskEnd(TaskAttemptID t) {}
   
  /**
   * Called when a task changes status. 
   * @param task the task whose status changed
   * @param taskStatus the new status of the task
   */
  public void statusUpdate(Task task, TaskStatus taskStatus) {}
}