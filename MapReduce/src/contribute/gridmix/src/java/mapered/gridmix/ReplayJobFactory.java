package org.apache.hadoop.mapred.gridmix;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.tools.rumen.JobStory;
import org.apache.hadoop.tools.rumen.JobStoryProducer;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

 class ReplayJobFactory extends JobFactory<Statistics.ClusterStats> {
  public static final Log LOG = LogFactory.getLog(ReplayJobFactory.class);

  /**
   * Creating a new instance does not start the thread.
   *
   * @param submitter   Component to which deserialized jobs are passed
   * @param jobProducer Job story producer
   *                    {@link org.apache.hadoop.tools.rumen.ZombieJobProducer}
   * @param scratch     Directory into which to write output from simulated jobs
   * @param conf        Config passed to all jobs to be submitted
   * @param startFlag   Latch released from main to start pipeline
   * @param resolver
   * @throws java.io.IOException
   */
  public ReplayJobFactory(
    JobSubmitter submitter, JobStoryProducer jobProducer, Path scratch,
    Configuration conf, CountDownLatch startFlag, UserResolver resolver)
    throws IOException {
    super(submitter, jobProducer, scratch, conf, startFlag, resolver);
  }

   
    @Override
  public Thread createReaderThread() {
    return new ReplayReaderThread("ReplayJobFactory");
  }

   /**
    * @param item
    */
   public void update(Statistics.ClusterStats item) {
   }

   private class ReplayReaderThread extends Thread {

    public ReplayReaderThread(String threadName) {
      super(threadName);
    }


    public void run() {
      try {
        startFlag.await();
        if (Thread.currentThread().isInterrupted()) {
          return;
        }
        final long initTime = TimeUnit.MILLISECONDS.convert(
          System.nanoTime(), TimeUnit.NANOSECONDS);
        LOG.info("START REPLAY @ " + initTime);
        long first = -1;
        long last = -1;
        while (!Thread.currentThread().isInterrupted()) {
          try {
            final JobStory job = getNextJobFiltered();
            if (null == job) {
              return;
            }
            if (first < 0) {
              first = job.getSubmissionTime();
            }
            final long current = job.getSubmissionTime();
            if (current < last) {
              LOG.warn("Job " + job.getJobID() + " out of order");
              continue;
            }
            last = current;
            submitter.add(
              jobCreator.createGridmixJob(
                conf, initTime + Math.round(rateFactor * (current - first)),
                job, scratch,
                userResolver.getTargetUgi(
                  UserGroupInformation.createRemoteUser(job.getUser())), 
                sequence.getAndIncrement()));
          } catch (IOException e) {
            error = e;
            return;
          }
        }
      } catch (InterruptedException e) {
        // exit thread; ignore any jobs remaining in the trace
      } finally {
        IOUtils.cleanup(null, jobProducer);
      }
    }
  }

   /**
    * Start the reader thread, wait for latch if necessary.
    */
   @Override
   public void start() {
     this.rThread.start();
   }

}
