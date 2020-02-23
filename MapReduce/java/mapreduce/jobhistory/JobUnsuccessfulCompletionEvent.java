package org.apache.hadoop.mapreduce.jobhistory;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.JobID;

import org.apache.avro.util.Utf8;

/**
 * Event to record Failed and Killed completion of jobs
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class JobUnsuccessfulCompletionEvent implements HistoryEvent {
  private JobUnsuccessfulCompletion datum
    = new JobUnsuccessfulCompletion();

  /**
   * Create an event to record unsuccessful completion (killed/failed) of jobs
   * @param id Job ID
   * @param finishTime Finish time of the job
   * @param finishedMaps Number of finished maps
   * @param finishedReduces Number of finished reduces
   * @param status Status of the job
   */
  public JobUnsuccessfulCompletionEvent(JobID id, long finishTime,
      int finishedMaps,
      int finishedReduces, String status) {
    datum.jobid = new Utf8(id.toString());
    datum.finishTime = finishTime;
    datum.finishedMaps = finishedMaps;
    datum.finishedReduces = finishedReduces;
    datum.jobStatus = new Utf8(status);
  }

  JobUnsuccessfulCompletionEvent() {}

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) {
    this.datum = (JobUnsuccessfulCompletion)datum;
  }

  /** Get the Job ID */
  public JobID getJobId() { return JobID.forName(datum.jobid.toString()); }
  /** Get the job finish time */
  public long getFinishTime() { return datum.finishTime; }
  /** Get the number of finished maps */
  public int getFinishedMaps() { return datum.finishedMaps; }
  /** Get the number of finished reduces */
  public int getFinishedReduces() { return datum.finishedReduces; }
  /** Get the status */
  public String getStatus() { return datum.jobStatus.toString(); }
  /** Get the event type */
  public EventType getEventType() {
    if ("FAILED".equals(getStatus())) {
      return EventType.JOB_FAILED;
    } else
      return EventType.JOB_KILLED;
  }

}
