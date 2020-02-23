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
 * Event to record successful completion of a map attempt
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class MapAttemptFinishedEvent  implements HistoryEvent {
  private MapAttemptFinished datum = new MapAttemptFinished();
  
  /** 
   * Create an event for successful completion of map attempts
   * @param id Task Attempt ID
   * @param taskType Type of the task
   * @param taskStatus Status of the task
   * @param mapFinishTime Finish time of the map phase
   * @param finishTime Finish time of the attempt
   * @param hostname Name of the host where the map executed
   * @param state State string for the attempt
   * @param counters Counters for the attempt
   */
  public MapAttemptFinishedEvent(TaskAttemptID id, 
      TaskType taskType, String taskStatus, 
      long mapFinishTime, long finishTime,
      String hostname, String state, Counters counters) {
    datum.taskid = new Utf8(id.getTaskID().toString());
    datum.attemptId = new Utf8(id.toString());
    datum.taskType = new Utf8(taskType.name());
    datum.taskStatus = new Utf8(taskStatus);
    datum.mapFinishTime = mapFinishTime;
    datum.finishTime = finishTime;
    datum.hostname = new Utf8(hostname);
    datum.state = new Utf8(state);
    datum.counters = EventWriter.toAvro(counters);
  }
  
  MapAttemptFinishedEvent() {}

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) {
    this.datum = (MapAttemptFinished)datum;
  }

  /** Get the task ID */
  public TaskID getTaskId() { return TaskID.forName(datum.taskid.toString()); }
  /** Get the attempt id */
  public TaskAttemptID getAttemptId() {
    return TaskAttemptID.forName(datum.attemptId.toString());
  }
  /** Get the task type */
  public TaskType getTaskType() {
    return TaskType.valueOf(datum.taskType.toString());
  }
  /** Get the task status */
  public String getTaskStatus() { return datum.taskStatus.toString(); }
  /** Get the map phase finish time */
  public long getMapFinishTime() { return datum.mapFinishTime; }
  /** Get the attempt finish time */
  public long getFinishTime() { return datum.finishTime; }
  /** Get the host name */
  public String getHostname() { return datum.hostname.toString(); }
  /** Get the state string */
  public String getState() { return datum.state.toString(); }
  /** Get the counters */
  Counters getCounters() { return EventReader.fromAvro(datum.counters); }
  /** Get the event type */
   public EventType getEventType() {
    return EventType.MAP_ATTEMPT_FINISHED;
  }
  
}
