package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.JobID;

import org.apache.avro.util.Utf8;

/**
 * Event to record the initialization of a job
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class JobInitedEvent implements HistoryEvent {
  private JobInited datum = new JobInited();

  /**
   * Create an event to record job initialization
   * @param id
   * @param launchTime
   * @param totalMaps
   * @param totalReduces
   * @param jobStatus
   */
  public JobInitedEvent(JobID id, long launchTime, int totalMaps,
                        int totalReduces, String jobStatus) {
    datum.jobid = new Utf8(id.toString());
    datum.launchTime = launchTime;
    datum.totalMaps = totalMaps;
    datum.totalReduces = totalReduces;
    datum.jobStatus = new Utf8(jobStatus);
  }

  JobInitedEvent() { }

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) { this.datum = (JobInited)datum; }

  /** Get the job ID */
  public JobID getJobId() { return JobID.forName(datum.jobid.toString()); }
  /** Get the launch time */
  public long getLaunchTime() { return datum.launchTime; }
  /** Get the total number of maps */
  public int getTotalMaps() { return datum.totalMaps; }
  /** Get the total number of reduces */
  public int getTotalReduces() { return datum.totalReduces; }
  /** Get the status */
  public String getStatus() { return datum.jobStatus.toString(); }
 /** Get the event type */
  public EventType getEventType() {
    return EventType.JOB_INITED;
  }

}
