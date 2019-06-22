package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Represents a directive from the {@link org.apache.hadoop.mapred.JobTracker} 
 * to the {@link org.apache.hadoop.mapred.TaskTracker} to commit the output
 * of the task.
 * 
 */
class CommitTaskAction extends TaskTrackerAction {
  private TaskAttemptID taskId;
  
  public CommitTaskAction() {
    super(ActionType.COMMIT_TASK);
    taskId = new TaskAttemptID();
  }
  
  public CommitTaskAction(TaskAttemptID taskId) {
    super(ActionType.COMMIT_TASK);
    this.taskId = taskId;
  }
  
  public TaskAttemptID getTaskID() {
    return taskId;
  }
  
  public void write(DataOutput out) throws IOException {
    taskId.write(out);
  }

  public void readFields(DataInput in) throws IOException {
    taskId.readFields(in);
  }
}
