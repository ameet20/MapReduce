package org.apache.hadoop.mapred.gridmix;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.tools.rumen.JobStoryProducer;
import org.apache.hadoop.mapred.gridmix.Statistics.JobStats;
import org.apache.hadoop.mapred.gridmix.Statistics.ClusterStats;

import java.util.concurrent.CountDownLatch;
import java.io.IOException;

enum GridmixJobSubmissionPolicy {

  REPLAY("REPLAY", 320000) {
    @Override
    public JobFactory<ClusterStats> createJobFactory(
      JobSubmitter submitter, JobStoryProducer producer, Path scratchDir,
      Configuration conf, CountDownLatch startFlag, UserResolver userResolver)
      throws IOException {
      return new ReplayJobFactory(
        submitter, producer, scratchDir, conf, startFlag, userResolver);
    }
  },

  STRESS("STRESS", 5000) {
    @Override
    public JobFactory<ClusterStats> createJobFactory(
      JobSubmitter submitter, JobStoryProducer producer, Path scratchDir,
      Configuration conf, CountDownLatch startFlag, UserResolver userResolver)
      throws IOException {
      return new StressJobFactory(
        submitter, producer, scratchDir, conf, startFlag, userResolver);
    }
  },

  SERIAL("SERIAL", 0) {
    @Override
    public JobFactory<JobStats> createJobFactory(
      JobSubmitter submitter, JobStoryProducer producer, Path scratchDir,
      Configuration conf, CountDownLatch startFlag, UserResolver userResolver)
      throws IOException {
      return new SerialJobFactory(
        submitter, producer, scratchDir, conf, startFlag, userResolver);
    }
  };

  public static final String JOB_SUBMISSION_POLICY =
    "gridmix.job-submission.policy";

  private final String name;
  private final int pollingInterval;

  GridmixJobSubmissionPolicy(String name, int pollingInterval) {
    this.name = name;
    this.pollingInterval = pollingInterval;
  }

  public abstract JobFactory createJobFactory(
    JobSubmitter submitter, JobStoryProducer producer, Path scratchDir,
    Configuration conf, CountDownLatch startFlag, UserResolver userResolver)
    throws IOException;

  public int getPollingInterval() {
    return pollingInterval;
  }

  public static GridmixJobSubmissionPolicy getPolicy(
    Configuration conf, GridmixJobSubmissionPolicy defaultPolicy) {
    String policy = conf.get(JOB_SUBMISSION_POLICY, defaultPolicy.name());
    return valueOf(policy.toUpperCase());
  }
}
