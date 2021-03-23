package org.apache.hadoop.contrib.index.mapred;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

/**
 * A class implements an index updater interface should create a Map/Reduce job
 * configuration and run the Map/Reduce job to analyze documents and update
 * Lucene instances in parallel.
 */
public interface IIndexUpdater {

  /**
   * Create a Map/Reduce job configuration and run the Map/Reduce job to
   * analyze documents and update Lucene instances in parallel.
   * @param conf
   * @param inputPaths
   * @param outputPath
   * @param numMapTasks
   * @param shards
   * @throws IOException
   */
  void run(Configuration conf, Path[] inputPaths, Path outputPath,
      int numMapTasks, Shard[] shards) throws IOException;

}
