package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.TaskID;
import org.apache.hadoop.mapreduce.TaskType;

import org.apache.avro.util.Utf8;

/**
 * Event to record the start of a task
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class TaskStartedEvent implements HistoryEvent {
  private TaskStarted datum = new TaskStarted();

  /**
   * Create an event to record start of a task
   * @param id Task Id
   * @param startTime Start time of the task
   * @param taskType Type of the task
   * @param splitLocations Split locations, applicable for map tasks
   */
  public TaskStartedEvent(TaskID id, long startTime, 
      TaskType taskType, String splitLocations) {
    datum.taskid = new Utf8(id.toString());
    datum.splitLocations = new Utf8(splitLocations);
    datum.startTime = startTime;
    datum.taskType = new Utf8(taskType.name());
  }

  TaskStartedEvent() {}

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) { this.datum = (TaskStarted)datum; }

  /** Get the task id */
  public TaskID getTaskId() { return TaskID.forName(datum.taskid.toString()); }
  /** Get the split locations, applicable for map tasks */
  public String getSplitLocations() { return datum.splitLocations.toString(); }
  /** Get the start time of the task */
  public long getStartTime() { return datum.startTime; }
  /** Get the task type */
  public TaskType getTaskType() {
    return TaskType.valueOf(datum.taskType.toString());
  }
  /** Get the event type */
  public EventType getEventType() {
    return EventType.TASK_STARTED;
  }

}
