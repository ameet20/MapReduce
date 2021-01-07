package org.apache.hadoop.mapred;

import java.util.Comparator;

/**
 * Order {@link JobInProgress} objects by priority and then by submit time, as
 * in the default scheduler in Hadoop.
 */
public class FifoJobComparator implements Comparator<JobInProgress> {
  public int compare(JobInProgress j1, JobInProgress j2) {
    int res = j1.getPriority().compareTo(j2.getPriority());
    if (res == 0) {
      if (j1.getStartTime() < j2.getStartTime()) {
        res = -1;
      } else {
        res = (j1.getStartTime() == j2.getStartTime() ? 0 : 1);
      }
    }
    if (res == 0) {
      // If there is a tie, break it by job ID to get a deterministic order
      res = j1.getJobID().compareTo(j2.getJobID());
    }
    return res;
  }
}
