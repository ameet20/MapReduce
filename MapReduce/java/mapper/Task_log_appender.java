package org.apache.hadoop.mapred;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.hadoop.classification.InterfaceStability;
import org.apache.log4j.FileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * A simple log4j-appender for the task child's 
 * map-reduce system logs.
 * 
 */
@InterfaceStability.Unstable
public class TaskLogAppender extends FileAppender {
  private String taskId; //taskId should be managed as String rather than TaskID object
  //so that log4j can configure it from the configuration(log4j.properties). 
  private Integer maxEvents;
  private Queue<LoggingEvent> tail = null;
  private Boolean isCleanup;

  // System properties passed in from JVM runner
  static final String ISCLEANUP_PROPERTY = "hadoop.tasklog.iscleanup";
  static final String LOGSIZE_PROPERTY = "hadoop.tasklog.totalLogFileSize";
  static final String TASKID_PROPERTY = "hadoop.tasklog.taskid";

  @Override
  public void activateOptions() {
    synchronized (this) {
      setOptionsFromSystemProperties();

      if (maxEvents > 0) {
        tail = new LinkedList<LoggingEvent>();
      }
      setFile(TaskLog.getTaskLogFile(TaskAttemptID.forName(taskId),
          isCleanup, TaskLog.LogName.SYSLOG).toString());
      setAppend(true);
      super.activateOptions();
    }
  }

  /**
   * The Task Runner passes in the options as system properties. Set
   * the options if the setters haven't already been called.
   */
  private synchronized void setOptionsFromSystemProperties() {
    if (isCleanup == null) {
      String propValue = System.getProperty(ISCLEANUP_PROPERTY, "false");
      isCleanup = Boolean.valueOf(propValue);
    }

    if (taskId == null) {
      taskId = System.getProperty(TASKID_PROPERTY);
    }

    if (maxEvents == null) {
      String propValue = System.getProperty(LOGSIZE_PROPERTY, "0");
      setTotalLogFileSize(Long.valueOf(propValue));
    }
  }
  
  @Override
  public void append(LoggingEvent event) {
    synchronized (this) {
      if (tail == null) {
        super.append(event);
      } else {
        if (tail.size() >= maxEvents) {
          tail.remove();
        }
        tail.add(event);
      }
    }
  }
  
  public void flush() {
    qw.flush();
  }

  @Override
  public synchronized void close() {
    if (tail != null) {
      for(LoggingEvent event: tail) {
        super.append(event);
      }
    }
    super.close();
  }

  /**
   * Getter/Setter methods for log4j.
   */
  
  public synchronized String getTaskId() {
    return taskId;
  }

  public synchronized void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  private static final int EVENT_SIZE = 100;
  
  public synchronized long getTotalLogFileSize() {
    return maxEvents * EVENT_SIZE;
  }

  public synchronized void setTotalLogFileSize(long logSize) {
    maxEvents = (int) logSize / EVENT_SIZE;
  }

  /**
   * Set whether the task is a cleanup attempt or not.
   * 
   * @param isCleanup
   *          true if the task is cleanup attempt, false otherwise.
   */
  public synchronized void setIsCleanup(boolean isCleanup) {
    this.isCleanup = isCleanup;
  }

  /**
   * Get whether task is cleanup attempt or not.
   * 
   * @return true if the task is cleanup attempt, false otherwise.
   */
  public synchronized boolean getIsCleanup() {
    return isCleanup;
  }
}
