package org.apache.hadoop.mapred;

import java.io.IOException;


/**
 * This exception is thrown when a tasktracker tries to register or communicate
 * with the jobtracker when it does not appear on the list of included nodes, 
 * or has been specifically excluded.
 * 
 */
class DisallowedTaskTrackerException extends IOException {

  private static final long serialVersionUID = 1L;

  public DisallowedTaskTrackerException(TaskTrackerStatus tracker) {
    super("Tasktracker denied communication with jobtracker: " + tracker.getTrackerName());
  }
}
