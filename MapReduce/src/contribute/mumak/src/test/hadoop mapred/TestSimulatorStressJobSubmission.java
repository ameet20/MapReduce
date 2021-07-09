package org.apache.hadoop.mapred;

public class TestSimulatorStressJobSubmission extends TestSimulatorEndToEnd {
  public TestSimulatorStressJobSubmission() {
    super();
    policy = SimulatorJobSubmissionPolicy.STRESS;
  }
}
