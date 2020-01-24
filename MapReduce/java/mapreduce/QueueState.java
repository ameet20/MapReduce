package org.apache.hadoop.mapreduce;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Enum representing queue state
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public enum QueueState {

  STOPPED("stopped"), RUNNING("running"), UNDEFINED("undefined");
  private final String stateName;
  private static Map<String, QueueState> enumMap =
      new HashMap<String, QueueState>();

  static {
    for (QueueState state : QueueState.values()) {
      enumMap.put(state.getStateName(), state);
    }
  }

  QueueState(String stateName) {
    this.stateName = stateName;
  }

  /**
   * @return the stateName
   */
  public String getStateName() {
    return stateName;
  }

  public static QueueState getState(String state) {
    QueueState qState = enumMap.get(state);
    if (qState == null) {
      return UNDEFINED;
    }
    return qState;
  }

  @Override
  public String toString() {
    return stateName;
  }

}
