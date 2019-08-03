package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * This class filters log files from directory given
 * It doesnt accept paths having _logs.
 * This can be used to list paths of output directory as follows:
 *   Path[] fileList = FileUtil.stat2Paths(fs.listStatus(outDir,
 *                                   new OutputLogFilter()));
 * @deprecated Use 
 *   {@link org.apache.hadoop.mapred.Utils.OutputFileUtils.OutputLogFilter} 
 *   instead.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class OutputLogFilter implements PathFilter {
  private static final PathFilter LOG_FILTER = 
    new Utils.OutputFileUtils.OutputLogFilter();
  public boolean accept(Path path) {
    return LOG_FILTER.accept(path);
  }
}
