package org.apache.hadoop.mapred;

import java.io.IOException;
import java.util.List;
import java.util.Collection;

import org.apache.hadoop.mapreduce.server.jobtracker.TaskTracker;

/**
 * Mock queue scheduler for testing only
 */
public class FakeDynamicScheduler extends QueueTaskScheduler {
  public void start() throws IOException {
  }
  public void terminate() throws IOException {
  }
  public List<Task> assignTasks(TaskTracker taskTracker)
    throws IOException {
    return null;
  }
  public Collection<JobInProgress> getJobs(String queueName) {
    return null;
  }
  public void setAllocator(QueueAllocator allocator) {
  }
}
