package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This is used to track task completion events on 
 * job tracker. 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.TaskCompletionEvent} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class TaskCompletionEvent 
    extends org.apache.hadoop.mapreduce.TaskCompletionEvent {
  @InterfaceAudience.Public
  @InterfaceStability.Stable
  static public enum Status {FAILED, KILLED, SUCCEEDED, OBSOLETE, TIPFAILED};
  
  public static final TaskCompletionEvent[] EMPTY_ARRAY = 
	    new TaskCompletionEvent[0];
  /**
   * Default constructor for Writable.
   *
   */
  public TaskCompletionEvent() {
    super();
  }

  /**
   * Constructor. eventId should be created externally and incremented
   * per event for each job. 
   * @param eventId event id, event id should be unique and assigned in
   *  incrementally, starting from 0. 
   * @param taskId task id
   * @param status task's status 
   * @param taskTrackerHttp task tracker's host:port for http. 
   */
  public TaskCompletionEvent(int eventId, 
                             TaskAttemptID taskId,
                             int idWithinJob,
                             boolean isMap,
                             Status status, 
                             String taskTrackerHttp){
    super(eventId, taskId, idWithinJob, isMap, org.apache.hadoop.mapreduce.
          TaskCompletionEvent.Status.valueOf(status.name()), taskTrackerHttp);
  }
  
  static TaskCompletionEvent downgrade(
    org.apache.hadoop.mapreduce.TaskCompletionEvent event) {
    return new TaskCompletionEvent(event.getEventId(),
      TaskAttemptID.downgrade(event.getTaskAttemptId()),event.idWithinJob(),
      event.isMapTask(), Status.valueOf(event.getStatus().name()),
      event.getTaskTrackerHttp());
  }
  /**
   * Returns task id. 
   * @return task id
   * @deprecated use {@link #getTaskAttemptId()} instead.
   */
  @Deprecated
  public String getTaskId() {
    return getTaskAttemptId().toString();
  }
  
  /**
   * Returns task id. 
   * @return task id
   */
  public TaskAttemptID getTaskAttemptId() {
    return TaskAttemptID.downgrade(super.getTaskAttemptId());
  }
  
  /**
   * Returns enum Status.SUCESS or Status.FAILURE.
   * @return task tracker status
   */
  public Status getTaskStatus() {
    return Status.valueOf(super.getStatus().name());
  }
  
  /**
   * Sets task id. 
   * @param taskId
   * @deprecated use {@link #setTaskAttemptId(TaskAttemptID)} instead.
   */
  @Deprecated
  public void setTaskId(String taskId) {
    this.setTaskAttemptId(TaskAttemptID.forName(taskId));
  }
  
  /**
   * Sets task id. 
   * @param taskId
   */
  protected void setTaskAttemptId(TaskAttemptID taskId) {
    super.setTaskAttemptId(taskId);
  }
  
  /**
   * Set task status. 
   * @param status
   */
  protected void setTaskStatus(Status status) {
    super.setTaskStatus(org.apache.hadoop.mapreduce.
      TaskCompletionEvent.Status.valueOf(status.name()));
  }
  
  /**
   * Set the task completion time
   * @param taskCompletionTime time (in millisec) the task took to complete
   */
  protected void setTaskRunTime(int taskCompletionTime) {
    super.setTaskRunTime(taskCompletionTime);
  }

  /**
   * set event Id. should be assigned incrementally starting from 0. 
   * @param eventId
   */
  protected void setEventId(int eventId) {
    super.setEventId(eventId);
  }

  /**
   * Set task tracker http location. 
   * @param taskTrackerHttp
   */
  protected void setTaskTrackerHttp(String taskTrackerHttp) {
    super.setTaskTrackerHttp(taskTrackerHttp);
  }
}