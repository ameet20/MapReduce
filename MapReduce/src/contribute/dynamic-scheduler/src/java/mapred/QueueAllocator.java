package org.apache.hadoop.mapred;

import java.util.Map;
/**
 * This interface is intended for allowing schedulers to 
 * communicate with the queue share management implementation.
 * Schedulers can periodically poll this interface to
 * obtain the latest queue allocations.
 */
public interface QueueAllocator {
  /**
   * Used by schedulers to obtain queue allocations periodically
   * @return hashtable of queue names and their allocations (shares)
   */
  Map<String,QueueAllocation> getAllocation();
  /**
   * Used by schedulers to push queue usage info for
   * accounting purposes.
   * @param queue the queue name
   * @param used of slots currently used
   * @param pending number of tasks pending
   */
  void setUsage(String queue, int used, int pending);
}
