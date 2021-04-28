package org.apache.hadoop.mapred;

/**
 * A clock class - can be mocked out for testing.
 */
class SimulatorClock extends Clock {

  long currentTime;

  SimulatorClock (long now) {
	  super();
	  currentTime = now;
  }
  void setTime(long now) {
    currentTime = now;
  }

  @Override
  long getTime() {
    return currentTime;
  }
}
