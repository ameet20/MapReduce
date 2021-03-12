package org.apache.hadoop.contrib.index.example;

import org.apache.hadoop.contrib.index.mapred.DocumentID;
import org.apache.hadoop.contrib.index.mapred.IDistributionPolicy;
import org.apache.hadoop.contrib.index.mapred.Shard;

/**
 * Choose a shard for each insert in a round-robin fashion. Choose all the
 * shards for each delete because we don't know where it is stored.
 */
public class RoundRobinDistributionPolicy implements IDistributionPolicy {

  private int numShards;
  private int rr; // round-robin implementation

  /* (non-Javadoc)
   * @see org.apache.hadoop.contrib.index.mapred.IDistributionPolicy#init(org.apache.hadoop.contrib.index.mapred.Shard[])
   */
  public void init(Shard[] shards) {
    numShards = shards.length;
    rr = 0;
  }

  /* (non-Javadoc)
   * @see org.apache.hadoop.contrib.index.mapred.IDistributionPolicy#chooseShardForInsert(org.apache.hadoop.contrib.index.mapred.DocumentID)
   */
  public int chooseShardForInsert(DocumentID key) {
    int chosen = rr;
    rr = (rr + 1) % numShards;
    return chosen;
  }

  /* (non-Javadoc)
   * @see org.apache.hadoop.contrib.index.mapred.IDistributionPolicy#chooseShardForDelete(org.apache.hadoop.contrib.index.mapred.DocumentID)
   */
  public int chooseShardForDelete(DocumentID key) {
    // -1 represents all the shards
    return -1;
  }
}
