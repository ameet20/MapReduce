package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.TaskID;
import org.apache.hadoop.mapreduce.TaskType;

import org.apache.avro.util.Utf8;

/**
 * Event to record start of a task attempt
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class TaskAttemptStartedEvent implements HistoryEvent {
  private TaskAttemptStarted datum = new TaskAttemptStarted();

  /**
   * Create an event to record the start of an attempt
   * @param attemptId Id of the attempt
   * @param taskType Type of task
   * @param startTime Start time of the attempt
   * @param trackerName Name of the Task Tracker where attempt is running
   * @param httpPort The port number of the tracker
   */
  public TaskAttemptStartedEvent( TaskAttemptID attemptId,  
      TaskType taskType, long startTime, String trackerName,
      int httpPort) {
    datum.attemptId = new Utf8(attemptId.toString());
    datum.taskid = new Utf8(attemptId.getTaskID().toString());
    datum.startTime = startTime;
    datum.taskType = new Utf8(taskType.name());
    datum.trackerName = new Utf8(trackerName);
    datum.httpPort = httpPort;
  }

  TaskAttemptStartedEvent() {}

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) {
    this.datum = (TaskAttemptStarted)datum;
  }

  /** Get the task id */
  public TaskID getTaskId() { return TaskID.forName(datum.taskid.toString()); }
  /** Get the tracker name */
  public String getTrackerName() { return datum.trackerName.toString(); }
  /** Get the start time */
  public long getStartTime() { return datum.startTime; }
  /** Get the task type */
  public TaskType getTaskType() {
    return TaskType.valueOf(datum.taskType.toString());
  }
  /** Get the HTTP port */
  public int getHttpPort() { return datum.httpPort; }
  /** Get the attempt id */
  public TaskAttemptID getTaskAttemptId() {
    return TaskAttemptID.forName(datum.attemptId.toString());
  }
  /** Get the event type */
  public EventType getEventType() {
    // Note that the task type can be setup/map/reduce/cleanup but the 
    // attempt-type can only be map/reduce.
   return getTaskId().getTaskType() == TaskType.MAP 
           ? EventType.MAP_ATTEMPT_STARTED 
           : EventType.REDUCE_ATTEMPT_STARTED;
  }

}
