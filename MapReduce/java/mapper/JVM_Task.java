package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;

/**
 * Task abstraction that can be serialized, implements the writable interface.
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class JvmTask implements Writable {
  Task t;
  boolean shouldDie;
  public JvmTask(Task t, boolean shouldDie) {
    this.t = t;
    this.shouldDie = shouldDie;
  }
  public JvmTask() {}
  public Task getTask() {
    return t;
  }
  public boolean shouldDie() {
    return shouldDie;
  }
  public void write(DataOutput out) throws IOException {
    out.writeBoolean(shouldDie);
    if (t != null) {
      out.writeBoolean(true);
      out.writeBoolean(t.isMapTask());
      t.write(out);
    } else {
      out.writeBoolean(false);
    }
  }
  public void readFields(DataInput in) throws IOException {
    shouldDie = in.readBoolean();
    boolean taskComing = in.readBoolean();
    if (taskComing) {
      boolean isMap = in.readBoolean();
      if (isMap) {
        t = new MapTask();
      } else {
        t = new ReduceTask();
      }
      t.readFields(in);
    }
  }
}
