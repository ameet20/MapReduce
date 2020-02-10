package org.apache.hadoop.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * Information about TaskTracker.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class TaskTrackerInfo implements Writable {
  String name;
  boolean isBlacklisted = false;
  String reasonForBlacklist = "";
  String blacklistReport = "";
  
  public TaskTrackerInfo() {
  }
  // construct an active tracker
  public TaskTrackerInfo(String name) {
    this.name = name;
  }

  // construct blacklisted tracker
  public TaskTrackerInfo(String name, String reasonForBlacklist,
      String report) {
    this.name = name;
    this.isBlacklisted = true;
    this.reasonForBlacklist = reasonForBlacklist;
    this.blacklistReport = report;
  }

  /**
   * Gets the tasktracker's name.
   * 
   * @return tracker's name.
   */
  public String getTaskTrackerName() {
    return name;
  }
  
  /**
   * Whether tracker is blacklisted
   * @return true if tracker is blacklisted
   *         false otherwise
   */
  public boolean isBlacklisted() {
    return isBlacklisted;
  }
  
  /**
   * Gets the reason for which the tasktracker was blacklisted.
   * 
   * @return reason which tracker was blacklisted
   */
  public String getReasonForBlacklist() {
    return reasonForBlacklist;
  }

  /**
   * Gets a descriptive report about why the tasktracker was blacklisted.
   * 
   * @return report describing why the tasktracker was blacklisted.
   */
  public String getBlacklistReport() {
    return blacklistReport;
  }
  
  @Override
  public void readFields(DataInput in) throws IOException {
    name = Text.readString(in);
    isBlacklisted = in.readBoolean();
    reasonForBlacklist = Text.readString(in);
    blacklistReport = Text.readString(in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    Text.writeString(out, name);
    out.writeBoolean(isBlacklisted);
    Text.writeString(out, reasonForBlacklist);
    Text.writeString(out, blacklistReport);
  }

}
