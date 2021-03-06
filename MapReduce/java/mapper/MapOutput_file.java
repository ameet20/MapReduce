package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.LocalDirAllocator;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.MRConfig;

/**
 * Manipulate the working area for the transient store for maps and reduces.
 *
 * This class is used by map and reduce tasks to identify the directories that
 * they need to write to/read from for intermediate files. The callers of
 * these methods are from child space and see mapreduce.cluster.local.dir as
 * taskTracker/jobCache/jobId/attemptId
 * This class should not be used from TaskTracker space.
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class MapOutputFile {

  private JobConf conf;

  static final String REDUCE_INPUT_FILE_FORMAT_STRING = "%s/map_%d.out";

  public MapOutputFile() {
  }

  private LocalDirAllocator lDirAlloc = 
    new LocalDirAllocator(MRConfig.LOCAL_DIR);
  
  /**
   * Return the path to local map output file created earlier
   * 
   * @return path
   * @throws IOException
   */
  public Path getOutputFile()
      throws IOException {
    return lDirAlloc.getLocalPathToRead(TaskTracker.OUTPUT + Path.SEPARATOR
        + "file.out", conf);
  }

  /**
   * Create a local map output file name.
   * 
   * @param size the size of the file
   * @return path
   * @throws IOException
   */
  public Path getOutputFileForWrite(long size)
      throws IOException {
    return lDirAlloc.getLocalPathForWrite(TaskTracker.OUTPUT + Path.SEPARATOR
        + "file.out", size, conf);
  }

  /**
   * Return the path to a local map output index file created earlier
   * 
   * @return path
   * @throws IOException
   */
  public Path getOutputIndexFile()
      throws IOException {
    return lDirAlloc.getLocalPathToRead(TaskTracker.OUTPUT + Path.SEPARATOR
        + "file.out.index", conf);
  }

  /**
   * Create a local map output index file name.
   * 
   * @param size the size of the file
   * @return path
   * @throws IOException
   */
  public Path getOutputIndexFileForWrite(long size)
      throws IOException {
    return lDirAlloc.getLocalPathForWrite(TaskTracker.OUTPUT + Path.SEPARATOR
        + "file.out.index", size, conf);
  }

  /**
   * Return a local map spill file created earlier.
   * 
   * @param spillNumber the number
   * @return path
   * @throws IOException
   */
  public Path getSpillFile(int spillNumber)
      throws IOException {
    return lDirAlloc.getLocalPathToRead(TaskTracker.OUTPUT + "/spill"
        + spillNumber + ".out", conf);
  }

  /**
   * Create a local map spill file name.
   * 
   * @param spillNumber the number
   * @param size the size of the file
   * @return path
   * @throws IOException
   */
  public Path getSpillFileForWrite(int spillNumber, long size)
      throws IOException {
    return lDirAlloc.getLocalPathForWrite(TaskTracker.OUTPUT + "/spill"
        + spillNumber + ".out", size, conf);
  }

  /**
   * Return a local map spill index file created earlier
   * 
   * @param spillNumber the number
   * @return path
   * @throws IOException
   */
  public Path getSpillIndexFile(int spillNumber)
      throws IOException {
    return lDirAlloc.getLocalPathToRead(TaskTracker.OUTPUT + "/spill"
        + spillNumber + ".out.index", conf);
  }

  /**
   * Create a local map spill index file name.
   * 
   * @param spillNumber the number
   * @param size the size of the file
   * @return path
   * @throws IOException
   */
  public Path getSpillIndexFileForWrite(int spillNumber, long size)
      throws IOException {
    return lDirAlloc.getLocalPathForWrite(TaskTracker.OUTPUT + "/spill"
        + spillNumber + ".out.index", size, conf);
  }

  /**
   * Return a local reduce input file created earlier
   * 
   * @param mapId a map task id
   * @return path
   * @throws IOException 
   */
  public Path getInputFile(int mapId)
      throws IOException {
    return lDirAlloc.getLocalPathToRead(String.format(
        REDUCE_INPUT_FILE_FORMAT_STRING, TaskTracker.OUTPUT, Integer
            .valueOf(mapId)), conf);
  }

  /**
   * Create a local reduce input file name.
   * 
   * @param mapId a map task id
   * @param size the size of the file
   * @return path
   * @throws IOException
   */
  public Path getInputFileForWrite(org.apache.hadoop.mapreduce.TaskID mapId, 
                                   long size)
      throws IOException {
    return lDirAlloc.getLocalPathForWrite(String.format(
        REDUCE_INPUT_FILE_FORMAT_STRING, TaskTracker.OUTPUT, mapId.getId()),
        size, conf);
  }

  /** Removes all of the files related to a task. */
  public void removeAll()
      throws IOException {
    conf.deleteLocalFiles(TaskTracker.OUTPUT);
  }

  public void setConf(Configuration conf) {
    if (conf instanceof JobConf) {
      this.conf = (JobConf) conf;
    } else {
      this.conf = new JobConf(conf);
    }
  }
  
}
