package org.apache.hadoop.mapreduce.task.reduce;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.mapreduce.TaskAttemptID;

class MapHost {
  
  public static enum State {
    IDLE,               // No map outputs available
    BUSY,               // Map outputs are being fetched
    PENDING,            // Known map outputs which need to be fetched
    PENALIZED           // Host penalized due to shuffle failures
  }
  
  private State state = State.IDLE;
  private final String hostName;
  private final String baseUrl;
  private List<TaskAttemptID> maps = new ArrayList<TaskAttemptID>();
  
  public MapHost(String hostName, String baseUrl) {
    this.hostName = hostName;
    this.baseUrl = baseUrl;
  }
  
  public State getState() {
    return state;
  }

  public String getHostName() {
    return hostName;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public synchronized void addKnownMap(TaskAttemptID mapId) {
    maps.add(mapId);
    if (state == State.IDLE) {
      state = State.PENDING;
    }
  }
  
  public synchronized List<TaskAttemptID> getAndClearKnownMaps() {
    List<TaskAttemptID> currentKnownMaps = maps;
    maps = new ArrayList<TaskAttemptID>();
    return currentKnownMaps;
  }
  
  public synchronized void markBusy() {
    state = State.BUSY;
  }
  
  public synchronized void markPenalized() {
    state = State.PENALIZED;
  }
  
  public synchronized int getNumKnownMapOutputs() {
    return maps.size();
  }

  /**
   * Called when the node is done with its penalty or done copying.
   * @return the host's new state
   */
  public synchronized State markAvailable() {
    if (maps.isEmpty()) {
      state = State.IDLE;
    } else {
      state = State.PENDING;
    }
    return state;
  }
  
  @Override
  public String toString() {
    return hostName;
  }
  
  /**
   * Mark the host as penalized
   */
  public synchronized void penalize() {
    state = State.PENALIZED;
  }
}
