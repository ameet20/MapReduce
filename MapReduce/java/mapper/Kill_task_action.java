package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


/**
 * Represents a directive from the {@link org.apache.hadoop.mapred.JobTracker} 
 * to the {@link org.apache.hadoop.mapred.TaskTracker} to kill a task.
 * 
 */
class KillTaskAction extends TaskTrackerAction {
  final TaskAttemptID taskId;
  
  public KillTaskAction() {
    super(ActionType.KILL_TASK);
    taskId = new TaskAttemptID();
  }
  
  public KillTaskAction(TaskAttemptID taskId) {
    super(ActionType.KILL_TASK);
    this.taskId = taskId;
  }

  public TaskAttemptID getTaskID() {
    return taskId;
  }
  
  @Override
  public void write(DataOutput out) throws IOException {
    taskId.write(out);
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    taskId.readFields(in);
  }
}
