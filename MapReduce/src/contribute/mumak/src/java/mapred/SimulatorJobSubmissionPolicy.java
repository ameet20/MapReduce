package org.apache.hadoop.mapred;

import org.apache.hadoop.conf.Configuration;

/**
 * Job submission policies. The set of policies is closed and encapsulated in
 * {@link SimulatorJobSubmissionPolicy}. The handling of submission policies is
 * embedded in the {@link SimulatorEngine} (through various events).
 * 
 */
public enum SimulatorJobSubmissionPolicy {
  /**
   * replay the trace by following the job inter-arrival rate faithfully.
   */
  REPLAY,
  
  /**
   * ignore submission time, keep submitting jobs until the cluster is saturated.
   */
  STRESS,
  
  /**
   * submitting jobs sequentially.
   */
  SERIAL;
  
  public static final String JOB_SUBMISSION_POLICY = "mumak.job-submission.policy";

  static public SimulatorJobSubmissionPolicy getPolicy(Configuration conf) {
    String policy = conf.get(JOB_SUBMISSION_POLICY, REPLAY.name());
    return valueOf(policy.toUpperCase());
  }
}
