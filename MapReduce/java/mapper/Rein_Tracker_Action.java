package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Represents a directive from the {@link org.apache.hadoop.mapred.JobTracker} 
 * to the {@link org.apache.hadoop.mapred.TaskTracker} to reinitialize itself.
 * 
 */
class ReinitTrackerAction extends TaskTrackerAction {

  public ReinitTrackerAction() {
    super(ActionType.REINIT_TRACKER);
  }
  
  public void write(DataOutput out) throws IOException {}

  public void readFields(DataInput in) throws IOException {}

}
