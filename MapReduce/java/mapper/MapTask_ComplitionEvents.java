package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;

/**
 * A class that represents the communication between the tasktracker and child
 * tasks w.r.t the map task completion events. It also indicates whether the
 * child task should reset its events index.
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class MapTaskCompletionEventsUpdate implements Writable {
  TaskCompletionEvent[] events;
  boolean reset;

  public MapTaskCompletionEventsUpdate() { }

  public MapTaskCompletionEventsUpdate(TaskCompletionEvent[] events,
      boolean reset) {
    this.events = events;
    this.reset = reset;
  }

  public boolean shouldReset() {
    return reset;
  }

  public TaskCompletionEvent[] getMapTaskCompletionEvents() {
    return events;
  }

  public void write(DataOutput out) throws IOException {
    out.writeBoolean(reset);
    out.writeInt(events.length);
    for (TaskCompletionEvent event : events) {
      event.write(out);
    }
  }

  public void readFields(DataInput in) throws IOException {
    reset = in.readBoolean();
    events = new TaskCompletionEvent[in.readInt()];
    for (int i = 0; i < events.length; ++i) {
      events[i] = new TaskCompletionEvent();
      events[i].readFields(in);
    }
  }
}
