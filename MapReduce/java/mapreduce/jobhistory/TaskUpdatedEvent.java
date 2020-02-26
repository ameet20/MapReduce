package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.TaskID;

import org.apache.avro.util.Utf8;

/**
 * Event to record updates to a task
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class TaskUpdatedEvent implements HistoryEvent {
  private TaskUpdated datum = new TaskUpdated();

  /**
   * Create an event to record task updates
   * @param id Id of the task
   * @param finishTime Finish time of the task
   */
  public TaskUpdatedEvent(TaskID id, long finishTime) {
    datum.taskid = new Utf8(id.toString());
    datum.finishTime = finishTime;
  }

  TaskUpdatedEvent() {}

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) { this.datum = (TaskUpdated)datum; }

  /** Get the task ID */
  public TaskID getTaskId() { return TaskID.forName(datum.taskid.toString()); }
  /** Get the task finish time */
  public long getFinishTime() { return datum.finishTime; }
  /** Get the event type */
  public EventType getEventType() {
    return EventType.TASK_UPDATED;
  }

}
