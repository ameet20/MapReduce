package org.apache.hadoop.mapred;

/**
 * A clock class - can be mocked out for testing.
 */
class Clock {
  long getTime() {
    return System.currentTimeMillis();
  }
}
