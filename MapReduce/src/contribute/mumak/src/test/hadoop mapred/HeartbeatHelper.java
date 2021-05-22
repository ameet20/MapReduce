package org.apache.hadoop.mapred;

import java.util.List;
import java.util.ArrayList;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//
// collection of heartbeat() parameters whose correctness we care about
// and the response we give if they are correct
//
public class HeartbeatHelper {
  public TaskTrackerStatus status = 
      new TaskTrackerStatus("dummytracker", "dummyhost");
  public boolean acceptNewTasks = true;
  public List<TaskTrackerAction> actions = new ArrayList<TaskTrackerAction>();
  
  static final Log LOG = LogFactory.getLog(HeartbeatHelper.class);
  
  public void addTaskTrackerAction(TaskTrackerAction action) {
    actions.add(action);
  }
  
  // adds an expected TaskStatus report
  public void addTaskReport(TaskStatus report) {
    // there is no setTaskReports() in TaskTrackerStatus, so we need to
    // create a new status object with a copy of all the other fields 
    String trackerName = status.getTrackerName();
    String host = status.getHost();
    int httpPort = status.getHttpPort();
    List<TaskStatus> taskReports = status.getTaskReports();
    int failures = status.getFailures();
    int maxMapTasks = status.getMaxMapSlots();
    int maxReduceTasks = status.getMaxReduceSlots();
    
    taskReports.add(report);
    status = new TaskTrackerStatus(trackerName, host, httpPort, taskReports,
                                   failures, maxMapTasks, maxReduceTasks);
  }
  
  public TaskTrackerAction[] getTaskTrackerActions() {
    return actions.toArray(new TaskTrackerAction[0]);
  }
  
  // checks most incoming parameters we care about
  public void checkHeartbeatParameters(TaskTrackerStatus otherStatus, 
                                       boolean otherAcceptNewTasks) {
    Assert.assertEquals("Mismatch in acceptNewTask", 
                 this.acceptNewTasks, otherAcceptNewTasks);

    List<TaskStatus> taskReports = this.status.getTaskReports(); 
    List<TaskStatus> otherTaskReports = otherStatus.getTaskReports(); 
    
    Assert.assertEquals("Mismatch in number of reported tasks",
                 taskReports.size(), otherTaskReports.size());
    for(TaskStatus report : taskReports) {
      boolean found = false;
      for(TaskStatus otherReport : otherTaskReports) {
        if(report.getTaskID() == otherReport.getTaskID()) {
          Assert.assertEquals("Map/reduce task mismatch",
                       report.getIsMap(), otherReport.getIsMap());
          Assert.assertEquals("Mismatch in run state",
                       report.getRunState(), otherReport.getRunState());
          Assert.assertEquals("Mismatch in run phase",
                       report.getPhase(), otherReport.getPhase());
          found = true;
          break;
        }
      }
      Assert.assertTrue("Task status report not found, taskID=" + 
                 report.getTaskID(), found);
    }
  }
}
