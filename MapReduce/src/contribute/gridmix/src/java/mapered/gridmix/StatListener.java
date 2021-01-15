package org.apache.hadoop.mapred.gridmix;

/**
 * Stat listener.
 * @param <T>
 */
interface StatListener<T>{

  /**
   * 
   * @param item
   */
  void update(T item);
}
