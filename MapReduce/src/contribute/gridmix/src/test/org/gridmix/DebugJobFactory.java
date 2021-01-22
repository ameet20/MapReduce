package org.apache.hadoop.mapred.gridmix;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.tools.rumen.JobStory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


/**
 * Component generating random job traces for testing on a single node.
 */
class DebugJobFactory {

  interface Debuggable {
    ArrayList<JobStory> getSubmitted();
  }

  public static JobFactory getFactory(
    JobSubmitter submitter, Path scratch, int numJobs, Configuration conf,
    CountDownLatch startFlag, UserResolver resolver) throws IOException {
    GridmixJobSubmissionPolicy policy = GridmixJobSubmissionPolicy.getPolicy(
      conf, GridmixJobSubmissionPolicy.STRESS);
    if (policy == GridmixJobSubmissionPolicy.REPLAY) {
      return new DebugReplayJobFactory(
        submitter, scratch, numJobs, conf, startFlag, resolver);
    } else if (policy == GridmixJobSubmissionPolicy.STRESS) {
      return new DebugStressJobFactory(
        submitter, scratch, numJobs, conf, startFlag, resolver);
    } else if (policy == GridmixJobSubmissionPolicy.SERIAL) {
      return new DebugSerialJobFactory(
        submitter, scratch, numJobs, conf, startFlag, resolver);

    }
    return null;
  }

  static class DebugReplayJobFactory extends ReplayJobFactory
    implements Debuggable {
    public DebugReplayJobFactory(
      JobSubmitter submitter, Path scratch, int numJobs, Configuration conf,
      CountDownLatch startFlag, UserResolver resolver) throws IOException {
      super(
        submitter, new DebugJobProducer(numJobs, conf), scratch, conf,
        startFlag, resolver);
    }

    @Override
    public ArrayList<JobStory> getSubmitted() {
      return ((DebugJobProducer) jobProducer).submitted;
    }

  }

  static class DebugSerialJobFactory extends SerialJobFactory
    implements Debuggable {
    public DebugSerialJobFactory(
      JobSubmitter submitter, Path scratch, int numJobs, Configuration conf,
      CountDownLatch startFlag, UserResolver resolver) throws IOException {
      super(
        submitter, new DebugJobProducer(numJobs, conf), scratch, conf,
        startFlag, resolver);
    }

    @Override
    public ArrayList<JobStory> getSubmitted() {
      return ((DebugJobProducer) jobProducer).submitted;
    }
  }

  static class DebugStressJobFactory extends StressJobFactory
    implements Debuggable {
    public DebugStressJobFactory(
      JobSubmitter submitter, Path scratch, int numJobs, Configuration conf,
      CountDownLatch startFlag, UserResolver resolver) throws IOException {
      super(
        submitter, new DebugJobProducer(numJobs, conf), scratch, conf,
        startFlag, resolver);
    }

    @Override
    public ArrayList<JobStory> getSubmitted() {
      return ((DebugJobProducer) jobProducer).submitted;
    }
  }

}
