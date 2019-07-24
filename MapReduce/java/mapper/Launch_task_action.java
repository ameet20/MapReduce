package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Represents a directive from the {@link org.apache.hadoop.mapred.JobTracker} 
 * to the {@link org.apache.hadoop.mapred.TaskTracker} to launch a new task.
 * 
 */
class LaunchTaskAction extends TaskTrackerAction {
  private Task task;

  public LaunchTaskAction() {
    super(ActionType.LAUNCH_TASK);
  }
  
  public LaunchTaskAction(Task task) {
    super(ActionType.LAUNCH_TASK);
    this.task = task;
  }
  
  public Task getTask() {
    return task;
  }
  
  public void write(DataOutput out) throws IOException {
    out.writeBoolean(task.isMapTask());
    task.write(out);
  }
  
  public void readFields(DataInput in) throws IOException {
    boolean isMapTask = in.readBoolean();
    if (isMapTask) {
      task = new MapTask();
    } else {
      task = new ReduceTask();
    }
    task.readFields(in);
  }

}
