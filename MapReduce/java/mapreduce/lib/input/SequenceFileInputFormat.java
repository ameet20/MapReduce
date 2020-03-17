package org.apache.hadoop.mapreduce.lib.input;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/** An {@link InputFormat} for {@link SequenceFile}s. */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class SequenceFileInputFormat<K, V> extends FileInputFormat<K, V> {

  @Override
  public RecordReader<K, V> createRecordReader(InputSplit split,
                                               TaskAttemptContext context
                                               ) throws IOException {
    return new SequenceFileRecordReader<K,V>();
  }

  @Override
  protected long getFormatMinSplitSize() {
    return SequenceFile.SYNC_INTERVAL;
  }

  @Override
  protected List<FileStatus> listStatus(JobContext job
                                        )throws IOException {

    List<FileStatus> files = super.listStatus(job);
    int len = files.size();
    for(int i=0; i < len; ++i) {
      FileStatus file = files.get(i);
      if (file.isDirectory()) {     // it's a MapFile
        Path p = file.getPath();
        FileSystem fs = p.getFileSystem(job.getConfiguration());
        // use the data file
        files.set(i, fs.getFileStatus(new Path(p, MapFile.DATA_FILE_NAME)));
      }
    }
    return files;
  }
}
