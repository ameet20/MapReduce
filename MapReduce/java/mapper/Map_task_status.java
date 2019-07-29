package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


class MapTaskStatus extends TaskStatus {

  private long mapFinishTime;
  private long sortFinishTime;
  
  public MapTaskStatus() {}

  public MapTaskStatus(TaskAttemptID taskid, float progress, int numSlots,
          State runState, String diagnosticInfo, String stateString,
          String taskTracker, Phase phase, Counters counters) {
    super(taskid, progress, numSlots, runState, diagnosticInfo, stateString,
          taskTracker, phase, counters);
  }

  @Override
  public boolean getIsMap() {
    return true;
  }

  /**
   * Sets finishTime. 
   * @param finishTime finish time of task.
   */
  @Override
  void setFinishTime(long finishTime) {
    super.setFinishTime(finishTime);
    if (mapFinishTime == 0) {
      mapFinishTime = finishTime;
    }
    setSortFinishTime(finishTime);
  }
  
  @Override
  public long getShuffleFinishTime() {
    throw new UnsupportedOperationException("getShuffleFinishTime() not supported for MapTask");
  }

  @Override
  void setShuffleFinishTime(long shuffleFinishTime) {
    throw new UnsupportedOperationException("setShuffleFinishTime() not supported for MapTask");
  }

  @Override
  public long getMapFinishTime() {
    return mapFinishTime;
  }
  
  @Override
  void setMapFinishTime(long mapFinishTime) {
    this.mapFinishTime = mapFinishTime;
  }

  @Override
  public long getSortFinishTime() {
    return sortFinishTime;
  }

  @Override
  void setSortFinishTime(long sortFinishTime) {
    this.sortFinishTime = sortFinishTime;
  }
  
  @Override
  synchronized void statusUpdate(TaskStatus status) {
    super.statusUpdate(status);
    
    if (status.getMapFinishTime() != 0) {
      this.mapFinishTime = status.getMapFinishTime();
    }
  }
  
  @Override
  public void readFields(DataInput in) throws IOException {
    super.readFields(in);
    mapFinishTime = in.readLong();
  }
  
  @Override
  public void write(DataOutput out) throws IOException {
    super.write(out);
    out.writeLong(mapFinishTime);
  }

  @Override
  public void addFetchFailedMap(TaskAttemptID mapTaskId) {
    throw new UnsupportedOperationException
                ("addFetchFailedMap() not supported for MapTask");
  }
}
