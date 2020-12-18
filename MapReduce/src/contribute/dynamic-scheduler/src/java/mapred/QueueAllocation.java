package org.apache.hadoop.mapred;

/**
 * Class to hold queue share info to be
 * communicated between scheduler and 
 * queue share manager
 */
public class QueueAllocation {
  private String name;
  private float share;
  /**
   * @param name queue name
   * @param share queue share of total capacity (0..1)
   */
  public QueueAllocation(String name, float share) {
    this.name = name;
    this.share = share;
  }
  /**
   * Gets queue share
   * @return queue share of total capacity (0..1)
   */
  public float getShare() {
    return this.share;
  }
  /**
   * Gets queue name
   * @return queue name
   */
  public String getName() {
    return this.name;
  }
}
