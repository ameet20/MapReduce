package org.apache.hadoop.contrib.index.mapred;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.util.Progressable;

/**
 * The record writer of this output format simply puts a message in an output
 * path when a shard update is done.
 */
public class IndexUpdateOutputFormat extends FileOutputFormat<Shard, Text> {

  /* (non-Javadoc)
   * @see FileOutputFormat#getRecordWriter(FileSystem, JobConf, String, Progressable)
   */
  public RecordWriter<Shard, Text> getRecordWriter(final FileSystem fs,
      JobConf job, String name, final Progressable progress)
      throws IOException {

    final Path perm = new Path(getWorkOutputPath(job), name);

    return new RecordWriter<Shard, Text>() {
      public void write(Shard key, Text value) throws IOException {
        assert (IndexUpdateReducer.DONE.equals(value));

        String shardName = key.getDirectory();
        shardName = shardName.replace("/", "_");

        Path doneFile =
            new Path(perm, IndexUpdateReducer.DONE + "_" + shardName);
        if (!fs.exists(doneFile)) {
          fs.createNewFile(doneFile);
        }
      }

      public void close(final Reporter reporter) throws IOException {
      }
    };
  }
}
