package org.apache.hadoop.contrib.index.mapred;

/**
 * A distribution policy decides, given a document with a document id, which
 * one shard the request should be sent to if the request is an insert, and
 * which shard(s) the request should be sent to if the request is a delete.
 */
public interface IDistributionPolicy {

  /**
   * Initialization. It must be called before any chooseShard() is called.
   * @param shards
   */
  void init(Shard[] shards);

  /**
   * Choose a shard to send an insert request.
   * @param key
   * @return the index of the chosen shard
   */
  int chooseShardForInsert(DocumentID key);

  /**
   * Choose a shard or all shards to send a delete request. E.g. a round-robin
   * distribution policy would send a delete request to all the shards.
   * -1 represents all the shards.
   * @param key
   * @return the index of the chosen shard, -1 if all the shards are chosen
   */
  int chooseShardForDelete(DocumentID key);

}
