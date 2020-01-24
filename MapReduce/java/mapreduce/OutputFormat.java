package org.apache.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.FileSystem;

/** 
 * <code>OutputFormat</code> describes the output-specification for a 
 * Map-Reduce job.
 *
 * <p>The Map-Reduce framework relies on the <code>OutputFormat</code> of the
 * job to:<p>
 * <ol>
 *   <li>
 *   Validate the output-specification of the job. For e.g. check that the 
 *   output directory doesn't already exist. 
 *   <li>
 *   Provide the {@link RecordWriter} implementation to be used to write out
 *   the output files of the job. Output files are stored in a 
 *   {@link FileSystem}.
 *   </li>
 * </ol>
 * 
 * @see RecordWriter
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class OutputFormat<K, V> {

  /** 
   * Get the {@link RecordWriter} for the given task.
   *
   * @param context the information about the current task.
   * @return a {@link RecordWriter} to write the output for the job.
   * @throws IOException
   */
  public abstract RecordWriter<K, V> 
    getRecordWriter(TaskAttemptContext context
                    ) throws IOException, InterruptedException;

  /** 
   * Check for validity of the output-specification for the job.
   *  
   * <p>This is to validate the output specification for the job when it is
   * a job is submitted.  Typically checks that it does not already exist,
   * throwing an exception when it already exists, so that output is not
   * overwritten.</p>
   *
   * @param context information about the job
   * @throws IOException when output should not be attempted
   */
  public abstract void checkOutputSpecs(JobContext context
                                        ) throws IOException, 
                                                 InterruptedException;

  /**
   * Get the output committer for this output format. This is responsible
   * for ensuring the output is committed correctly.
   * @param context the task context
   * @return an output committer
   * @throws IOException
   * @throws InterruptedException
   */
  public abstract 
  OutputCommitter getOutputCommitter(TaskAttemptContext context
                                     ) throws IOException, InterruptedException;
}
