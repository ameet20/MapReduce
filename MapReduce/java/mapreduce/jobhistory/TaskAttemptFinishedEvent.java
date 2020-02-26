package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.TaskID;
import org.apache.hadoop.mapreduce.TaskType;

import org.apache.avro.util.Utf8;

/**
 * Event to record successful task completion
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class TaskAttemptFinishedEvent  implements HistoryEvent {
  private TaskAttemptFinished datum = new TaskAttemptFinished();

  /**
   * Create an event to record successful finishes for setup and cleanup 
   * attempts
   * @param id Attempt ID
   * @param taskType Type of task
   * @param taskStatus Status of task
   * @param finishTime Finish time of attempt
   * @param hostname Host where the attempt executed
   * @param state State string
   * @param counters Counters for the attempt
   */
  public TaskAttemptFinishedEvent(TaskAttemptID id, 
      TaskType taskType, String taskStatus, 
      long finishTime,
      String hostname, String state, Counters counters) {
    datum.taskid = new Utf8(id.getTaskID().toString());
    datum.attemptId = new Utf8(id.toString());
    datum.taskType = new Utf8(taskType.name());
    datum.taskStatus = new Utf8(taskStatus);
    datum.finishTime = finishTime;
    datum.hostname = new Utf8(hostname);
    datum.state = new Utf8(state);
    datum.counters = EventWriter.toAvro(counters);
  }

  TaskAttemptFinishedEvent() {}

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) {
    this.datum = (TaskAttemptFinished)datum;
  }

  /** Get the task ID */
  public TaskID getTaskId() { return TaskID.forName(datum.taskid.toString()); }
  /** Get the task attempt id */
  public TaskAttemptID getAttemptId() {
    return TaskAttemptID.forName(datum.attemptId.toString());
  }
  /** Get the task type */
  public TaskType getTaskType() {
    return TaskType.valueOf(datum.taskType.toString());
  }
  /** Get the task status */
  public String getTaskStatus() { return datum.taskStatus.toString(); }
  /** Get the attempt finish time */
  public long getFinishTime() { return datum.finishTime; }
  /** Get the host where the attempt executed */
  public String getHostname() { return datum.hostname.toString(); }
  /** Get the state string */
  public String getState() { return datum.state.toString(); }
  /** Get the counters for the attempt */
  Counters getCounters() { return EventReader.fromAvro(datum.counters); }
  /** Get the event type */
  public EventType getEventType() {
    // Note that the task type can be setup/map/reduce/cleanup but the 
    // attempt-type can only be map/reduce.
    return getTaskId().getTaskType() == TaskType.MAP 
           ? EventType.MAP_ATTEMPT_FINISHED
           : EventType.REDUCE_ATTEMPT_FINISHED;
  }

}
