package org.apache.hadoop.mapred;

import org.apache.hadoop.tools.rumen.TaskAttemptInfo;

/**
 * This class is used to augment {@link LaunchTaskAction} with run time statistics 
 * and the final task state (successfull xor failed).
 */
class SimulatorLaunchTaskAction extends LaunchTaskAction {
  /**
   * Run time resource usage of the task.
   */
  private TaskAttemptInfo taskAttemptInfo;

  /**
   * Constructs a SimulatorLaunchTaskAction object for a {@link Task}.
   * @param task Task task to be launched
   * @param taskAttemptInfo resource usage model for task execution
   */            
  public SimulatorLaunchTaskAction(Task task,
                                   TaskAttemptInfo taskAttemptInfo) {
    super(task);
    this.taskAttemptInfo = taskAttemptInfo;
  }
  
  /** Get the resource usage model for the task. */
  public TaskAttemptInfo getTaskAttemptInfo() {
    return taskAttemptInfo;
  }
  
  @Override
  public String toString() {
    return this.getClass().getName() + "[taskID=" + 
           this.getTask().getTaskID() + "]";
  }
}
