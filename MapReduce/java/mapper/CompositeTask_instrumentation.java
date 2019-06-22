package org.apache.hadoop.mapred;

import java.io.File;
import java.util.List;

/**
 * This TaskTrackerInstrumentation subclass forwards all the events it
 * receives to a list of instrumentation objects, and can thus be used to
 * attack multiple instrumentation objects to a TaskTracker.
 */
class CompositeTaskTrackerInstrumentation extends TaskTrackerInstrumentation {
  
  private List<TaskTrackerInstrumentation> instrumentations;

  public CompositeTaskTrackerInstrumentation(TaskTracker tt,
      List<TaskTrackerInstrumentation> instrumentations) {
    super(tt);
    this.instrumentations = instrumentations;
  }

  // Package-private getter methods for tests
  List<TaskTrackerInstrumentation> getInstrumentations() {
    return instrumentations;
  }
  
  @Override
  public void completeTask(TaskAttemptID t) {
    for (TaskTrackerInstrumentation tti: instrumentations) {
      tti.completeTask(t);
    }
  }
  
  @Override
  public void timedoutTask(TaskAttemptID t) {
    for (TaskTrackerInstrumentation tti: instrumentations) {
      tti.timedoutTask(t);
    }
  }
  
  @Override
  public void taskFailedPing(TaskAttemptID t) {
    for (TaskTrackerInstrumentation tti: instrumentations) {
      tti.taskFailedPing(t);
    }
  }

  @Override
  public void reportTaskLaunch(TaskAttemptID t, File stdout, File stderr) {
    for (TaskTrackerInstrumentation tti: instrumentations) {
      tti.reportTaskLaunch(t, stdout, stderr);
    }
  }
  
  @Override
  public void reportTaskEnd(TaskAttemptID t) {
    for (TaskTrackerInstrumentation tti: instrumentations) {
      tti.reportTaskEnd(t);
    }
  }
   
  @Override
  public void statusUpdate(Task task, TaskStatus taskStatus) {
    for (TaskTrackerInstrumentation tti: instrumentations) {
      tti.statusUpdate(task, taskStatus);
    }
  }
}
