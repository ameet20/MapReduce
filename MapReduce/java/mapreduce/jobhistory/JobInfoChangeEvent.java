package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.JobID;

import org.apache.avro.util.Utf8;

/**
 * Event to record changes in the submit and launch time of
 * a job
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class JobInfoChangeEvent implements HistoryEvent {
  private JobInfoChange datum = new JobInfoChange();

  /** 
   * Create a event to record the submit and launch time of a job
   * @param id Job Id 
   * @param submitTime Submit time of the job
   * @param launchTime Launch time of the job
   */
  public JobInfoChangeEvent(JobID id, long submitTime, long launchTime) {
    datum.jobid = new Utf8(id.toString());
    datum.submitTime = submitTime;
    datum.launchTime = launchTime;
  }

  JobInfoChangeEvent() { }

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) {
    this.datum = (JobInfoChange)datum;
  }

  /** Get the Job ID */
  public JobID getJobId() { return JobID.forName(datum.jobid.toString()); }
  /** Get the Job submit time */
  public long getSubmitTime() { return datum.submitTime; }
  /** Get the Job launch time */
  public long getLaunchTime() { return datum.launchTime; }

  public EventType getEventType() {
    return EventType.JOB_INFO_CHANGED;
  }

}
