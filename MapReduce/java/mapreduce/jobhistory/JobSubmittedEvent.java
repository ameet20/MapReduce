package org.apache.hadoop.mapreduce.jobhistory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.JobACL;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.security.authorize.AccessControlList;

import org.apache.avro.util.Utf8;

/**
 * Event to record the submission of a job
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class JobSubmittedEvent implements HistoryEvent {
  private JobSubmitted datum = new JobSubmitted();

  /**
   * Create an event to record job submission
   * @param id The job Id of the job
   * @param jobName Name of the job
   * @param userName Name of the user who submitted the job
   * @param submitTime Time of submission
   * @param jobConfPath Path of the Job Configuration file
   * @param jobACLs The configured acls for the job.
   * @param jobQueueName The job-queue to which this job was submitted to
   */
  public JobSubmittedEvent(JobID id, String jobName, String userName,
      long submitTime, String jobConfPath,
      Map<JobACL, AccessControlList> jobACLs, String jobQueueName) {
    datum.jobid = new Utf8(id.toString());
    datum.jobName = new Utf8(jobName);
    datum.userName = new Utf8(userName);
    datum.submitTime = submitTime;
    datum.jobConfPath = new Utf8(jobConfPath);
    Map<Utf8, Utf8> jobAcls = new HashMap<Utf8, Utf8>();
    for (Entry<JobACL, AccessControlList> entry : jobACLs.entrySet()) {
      jobAcls.put(new Utf8(entry.getKey().getAclName()), new Utf8(
          entry.getValue().getAclString()));
    }
    datum.acls = jobAcls;
    if (jobQueueName != null) {
      datum.jobQueueName = new Utf8(jobQueueName);
    }
  }

  JobSubmittedEvent() {}

  public Object getDatum() { return datum; }
  public void setDatum(Object datum) {
    this.datum = (JobSubmitted)datum;
  }

  /** Get the Job Id */
  public JobID getJobId() { return JobID.forName(datum.jobid.toString()); }
  /** Get the Job name */
  public String getJobName() { return datum.jobName.toString(); }
  /** Get the Job queue name */
  public String getJobQueueName() {
    if (datum.jobQueueName != null) {
      return datum.jobQueueName.toString();
    }
    return null;
  }
  /** Get the user name */
  public String getUserName() { return datum.userName.toString(); }
  /** Get the submit time */
  public long getSubmitTime() { return datum.submitTime; }
  /** Get the Path for the Job Configuration file */
  public String getJobConfPath() { return datum.jobConfPath.toString(); }
  /** Get the acls configured for the job **/
  public Map<JobACL, AccessControlList> getJobAcls() {
    Map<JobACL, AccessControlList> jobAcls =
        new HashMap<JobACL, AccessControlList>();
    for (JobACL jobACL : JobACL.values()) {
      Utf8 jobACLsUtf8 = new Utf8(jobACL.getAclName());
      if (datum.acls.containsKey(jobACLsUtf8)) {
        jobAcls.put(jobACL, new AccessControlList(datum.acls.get(
            jobACLsUtf8).toString()));
      }
    }
    return jobAcls;
  }
  /** Get the event type */
  public EventType getEventType() { return EventType.JOB_SUBMITTED; }

}
