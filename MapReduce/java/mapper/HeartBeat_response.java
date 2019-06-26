package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.mapreduce.server.jobtracker.JTConfig;

/**
 * The response sent by the {@link JobTracker} to the hearbeat sent
 * periodically by the {@link TaskTracker}
 * 
 */
class HeartbeatResponse implements Writable, Configurable {
  Configuration conf = null;
  short responseId;
  int heartbeatInterval;
  TaskTrackerAction[] actions;

  HeartbeatResponse() {}
  
  HeartbeatResponse(short responseId, TaskTrackerAction[] actions) {
    this.responseId = responseId;
    this.actions = actions;
    this.heartbeatInterval = JTConfig.JT_HEARTBEAT_INTERVAL_MIN_DEFAULT;
  }
  
  public void setResponseId(short responseId) {
    this.responseId = responseId; 
  }
  
  public short getResponseId() {
    return responseId;
  }

  public void setActions(TaskTrackerAction[] actions) {
    this.actions = actions;
  }
  
  public TaskTrackerAction[] getActions() {
    return actions;
  }
  
  public void setConf(Configuration conf) {
    this.conf = conf;
  }

  public Configuration getConf() {
    return conf;
  }

  public void setHeartbeatInterval(int interval) {
    this.heartbeatInterval = interval;
  }
  
  public int getHeartbeatInterval() {
    return heartbeatInterval;
  }
  
  public void write(DataOutput out) throws IOException {
    out.writeShort(responseId);
    out.writeInt(heartbeatInterval);
    if (actions == null) {
      WritableUtils.writeVInt(out, 0);
    } else {
      WritableUtils.writeVInt(out, actions.length);
      for (TaskTrackerAction action : actions) {
        WritableUtils.writeEnum(out, action.getActionId());
        action.write(out);
      }
    }
  }
  
  public void readFields(DataInput in) throws IOException {
    this.responseId = in.readShort();
    this.heartbeatInterval = in.readInt();
    int length = WritableUtils.readVInt(in);
    if (length > 0) {
      actions = new TaskTrackerAction[length];
      for (int i=0; i < length; ++i) {
        TaskTrackerAction.ActionType actionType = 
          WritableUtils.readEnum(in, TaskTrackerAction.ActionType.class);
        actions[i] = TaskTrackerAction.createAction(actionType);
        actions[i].readFields(in);
      }
    } else {
      actions = null;
    }
  }
}
