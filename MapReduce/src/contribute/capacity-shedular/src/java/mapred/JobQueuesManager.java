package org.apache.hadoop.mapred;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.JobQueueJobInProgressListener.JobSchedulingInfo;

/**
 * A {@link JobInProgressListener} that maintains the jobs being managed in
 * one or more queues.
 */
class JobQueuesManager extends JobInProgressListener {

  // we maintain a hashmap of queue-names to queue info
  private Map<String, JobQueue> jobQueues =
    new HashMap<String, JobQueue>();
  private static final Log LOG = LogFactory.getLog(JobQueuesManager.class);


  JobQueuesManager() {
  }

  /**
   * Add the given queue to the map of queue name to job-queues.
   * 
   * @param queue The job-queue
   */
  public void addQueue(JobQueue queue) {
    jobQueues.put(queue.getName(),queue);
  }

  @Override
  public void jobAdded(JobInProgress job) throws IOException {
    LOG.info("Job " + job.getJobID() + " submitted to queue "
        + job.getProfile().getQueueName());
    // add job to the right queue
    JobQueue qi = getJobQueue(job.getProfile().getQueueName());
    if (null == qi) {
      // job was submitted to a queue we're not aware of
      LOG.warn(
        "Invalid queue " + job.getProfile().getQueueName() +
          " specified for job " + job.getProfile().getJobID() +
          ". Ignoring job.");
      return;
    }
    // let scheduler know. 
    qi.jobAdded(job);
  }

  // Note that job is removed when the job completes i.e in jobUpated()
  @Override
  public void jobRemoved(JobInProgress job) {
  }


  @Override
  public void jobUpdated(JobChangeEvent event) {
    JobInProgress job = event.getJobInProgress();
    JobQueue qi = getJobQueue(job.getProfile().getQueueName());
    qi.jobUpdated(event);

  }

  Comparator<JobSchedulingInfo> getComparator(String queue) {
    return getJobQueue(queue).comparator;
  }


  public JobQueue getJobQueue(JobInProgress jip){
    return getJobQueue(jip.getProfile().getQueueName());   
  }

  JobQueue getJobQueue(String name) {
    return jobQueues.get(name);
  }

  public Set<String> getJobQueueNames() {
    return jobQueues.keySet();
  }
}
