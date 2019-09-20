package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * A utility class. It provides
 *   A path filter utility to filter out output/part files in the output dir
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class Utils {
  public static class OutputFileUtils {
    /**
     * This class filters output(part) files from the given directory
     * It does not accept files with filenames _logs and _SUCCESS.
     * This can be used to list paths of output directory as follows:
     *   Path[] fileList = FileUtil.stat2Paths(fs.listStatus(outDir,
     *                                         new OutputFilesFilter()));
     */
    public static class OutputFilesFilter extends OutputLogFilter {
      public boolean accept(Path path) {
        return super.accept(path) 
               && !FileOutputCommitter.SUCCEEDED_FILE_NAME
                   .equals(path.getName());
      }
    }
    
    /**
     * This class filters log files from directory given
     * It doesnt accept paths having _logs.
     * This can be used to list paths of output directory as follows:
     *   Path[] fileList = FileUtil.stat2Paths(fs.listStatus(outDir,
     *                                   new OutputLogFilter()));
     */
    public static class OutputLogFilter implements PathFilter {
      public boolean accept(Path path) {
        return !"_logs".equals(path.getName());
      }
    }
  }
}
