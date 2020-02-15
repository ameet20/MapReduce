package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapred.JobPriority;
import org.apache.hadoop.mapreduce.JobID;

import org.apache.avro.util.Utf8;

/**
 * Event to record the change of priority of a job
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class JobPriorityChangeEvent implements HistoryEvent {
  private JobPriorityChange datum = new JobPriorityChange();

  /** Generate an event to record changes in Job priority
   * @param id Job Id
   * @param priority The new priority of the job
   */
  public JobPriorityChangeEvent(JobID id, JobPriority priority) {
    datum.jobid = new Utf8(id.toString());
    datum.priority = new Utf8(priority.name());
  }

  JobPriorityChangeEvent() { }

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) {
    this.datum = (JobPriorityChange)datum;
  }

  /** Get the Job ID */
  public JobID getJobId() { return JobID.forName(datum.jobid.toString()); }
  /** Get the job priority */
  public JobPriority getPriority() {
    return JobPriority.valueOf(datum.priority.toString());
  }
  /** Get the event type */
  public EventType getEventType() {
    return EventType.JOB_PRIORITY_CHANGED;
  }

}
