package org.apache.hadoop.mapred;

import junit.framework.TestCase;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.io.File;

import org.apache.hadoop.conf.Configuration;

/**
 * Base class for various scheduler tests
 */
public class BaseSchedulerTest extends TestCase {
  final static String[] QUEUES = new String[] {"queue1","queue2"};
  protected FakeDynamicTimer timer = new FakeDynamicTimer();
  protected FakeTaskTrackerManager taskTracker = new FakeTaskTrackerManager();
  protected String budgetFile;
  protected Configuration conf;
   /**
   * Create the test budget file
   * @throws Exception
   */
  @Override
  protected void setUp() throws Exception {
    super.setUp();
     String pathname = System.getProperty("test.build.data",
                                          "build/contrib/dynamic-scheduler/test/data");
     String testDir = new File(pathname).getAbsolutePath();
    budgetFile = new File(testDir, "test-budget").getAbsolutePath();
    new File(testDir).mkdirs();
    new File(budgetFile).createNewFile();
         conf = new Configuration();
    conf.set(PrioritySchedulerOptions.DYNAMIC_SCHEDULER_ALLOC_INTERVAL, "2");
    conf.set(PrioritySchedulerOptions.DYNAMIC_SCHEDULER_BUDGET_FILE, budgetFile);
  }

  /**
    * deletes the test budget file
    * @throws Exception
    */
   @Override
   protected void tearDown() throws Exception {
     new File(budgetFile).delete();
   }

  static class FakeTaskTrackerManager implements TaskTrackerManager {
    FakeQueueManager qm = new FakeQueueManager();
    public FakeTaskTrackerManager() {
    }
    public void addTaskTracker(String ttName) {
    }
    public ClusterStatus getClusterStatus() {
      return null;
    }
    public int getNumberOfUniqueHosts() {
      return 0;
    }
    public int getNextHeartbeatInterval() {
      return 0;
    }
    public Collection<TaskTrackerStatus> taskTrackers() {
      return null;
    }
    public void addJobInProgressListener(JobInProgressListener listener) {
    }
    public void removeJobInProgressListener(JobInProgressListener listener) {
    }
    public void submitJob(JobInProgress job) {
    }
    public TaskTrackerStatus getTaskTracker(String trackerID) {
      return null;
    }
    public void killJob(JobID jobid) throws IOException {
    }
    public JobInProgress getJob(JobID jobid) {
      return null;
    }
    public void initJob(JobInProgress job) {
    }
    public void failJob(JobInProgress job) {
    }
    public void startTask(String taskTrackerName, final Task t) {
    }
    public boolean killTask(TaskAttemptID attemptId, boolean shouldFail) {
      return true;
    }
    void addQueues(String[] arr) {
      Set<String> queues = new HashSet<String>();
      queues.addAll(Arrays.asList(arr));
      qm.setQueues(queues);
    }
    public QueueManager getQueueManager() {
      return qm;
    }
  }




  static class FakeDynamicTimer extends Timer {
    private TimerTask task;
    public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
      this.task = task;
    }
    public void runTask() {
      task.run();
    }
  }

  static class FakeQueueManager extends QueueManager {
    private Set<String> queues = null;
    FakeQueueManager() {
      super(new Configuration());
    }
    void setQueues(Set<String> queues) {
      this.queues = queues;
    }
    public synchronized Set<String> getLeafQueueNames() {
      return queues;
    }
  }

}
