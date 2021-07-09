package org.apache.hadoop.mapred;

public class TestSimulatorSerialJobSubmission extends TestSimulatorEndToEnd {
  public TestSimulatorSerialJobSubmission() {
    super();
    policy = SimulatorJobSubmissionPolicy.SERIAL;
  }
}
