package org.apache.hadoop.contrib.index.example;

import org.apache.hadoop.contrib.index.mapred.DocumentID;
import org.apache.hadoop.contrib.index.mapred.IDistributionPolicy;
import org.apache.hadoop.contrib.index.mapred.Shard;

/**
 * Choose a shard for each insert or delete based on document id hashing. Do
 * NOT use this distribution policy when the number of shards changes.
 */
public class HashingDistributionPolicy implements IDistributionPolicy {

  private int numShards;

  /* (non-Javadoc)
   * @see org.apache.hadoop.contrib.index.mapred.IDistributionPolicy#init(org.apache.hadoop.contrib.index.mapred.Shard[])
   */
  public void init(Shard[] shards) {
    numShards = shards.length;
  }

  /* (non-Javadoc)
   * @see org.apache.hadoop.contrib.index.mapred.IDistributionPolicy#chooseShardForInsert(org.apache.hadoop.contrib.index.mapred.DocumentID)
   */
  public int chooseShardForInsert(DocumentID key) {
    int hashCode = key.hashCode();
    return hashCode >= 0 ? hashCode % numShards : (-hashCode) % numShards;
  }

  /* (non-Javadoc)
   * @see org.apache.hadoop.contrib.index.mapred.IDistributionPolicy#chooseShardForDelete(org.apache.hadoop.contrib.index.mapred.DocumentID)
   */
  public int chooseShardForDelete(DocumentID key) {
    int hashCode = key.hashCode();
    return hashCode >= 0 ? hashCode % numShards : (-hashCode) % numShards;
  }

}
