package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.MapFile;

/** An {@link InputFormat} for {@link SequenceFile}s. 
 * @deprecated Use 
 *  {@link org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat} 
 *  instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class SequenceFileInputFormat<K, V> extends FileInputFormat<K, V> {

  public SequenceFileInputFormat() {
    setMinSplitSize(SequenceFile.SYNC_INTERVAL);
  }
  
  @Override
  protected FileStatus[] listStatus(JobConf job) throws IOException {
    FileStatus[] files = super.listStatus(job);
    for (int i = 0; i < files.length; i++) {
      FileStatus file = files[i];
      if (file.isDirectory()) {     // it's a MapFile
        Path dataFile = new Path(file.getPath(), MapFile.DATA_FILE_NAME);
        FileSystem fs = file.getPath().getFileSystem(job);
        // use the data file
        files[i] = fs.getFileStatus(dataFile);
      }
    }
    return files;
  }

  public RecordReader<K, V> getRecordReader(InputSplit split,
                                      JobConf job, Reporter reporter)
    throws IOException {

    reporter.setStatus(split.toString());

    return new SequenceFileRecordReader<K, V>(job, (FileSplit) split);
  }

}
