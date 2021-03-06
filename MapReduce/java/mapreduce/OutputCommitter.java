package org.apache.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
/**
 * <code>OutputCommitter</code> describes the commit of task output for a 
 * Map-Reduce job.
 *
 * <p>The Map-Reduce framework relies on the <code>OutputCommitter</code> of 
 * the job to:<p>
 * <ol>
 *   <li>
 *   Setup the job during initialization. For example, create the temporary 
 *   output directory for the job during the initialization of the job.
 *   </li>
 *   <li>
 *   Cleanup the job after the job completion. For example, remove the
 *   temporary output directory after the job completion. 
 *   </li>
 *   <li>
 *   Setup the task temporary output.
 *   </li> 
 *   <li>
 *   Check whether a task needs a commit. This is to avoid the commit
 *   procedure if a task does not need commit.
 *   </li>
 *   <li>
 *   Commit of the task output.
 *   </li>  
 *   <li>
 *   Discard the task commit.
 *   </li>
 * </ol>
 * 
 * @see org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter 
 * @see JobContext
 * @see TaskAttemptContext 
 *
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class OutputCommitter {
  /**
   * For the framework to setup the job output during initialization
   * 
   * @param jobContext Context of the job whose output is being written.
   * @throws IOException if temporary output could not be created
   */
  public abstract void setupJob(JobContext jobContext) throws IOException;

  /**
   * For cleaning up the job's output after job completion
   * 
   * @param jobContext Context of the job whose output is being written.
   * @throws IOException
   * @deprecated Use {@link #commitJob(JobContext)} or
   *                 {@link #abortJob(JobContext, JobStatus.State)} instead.
   */
  @Deprecated
  public void cleanupJob(JobContext jobContext) throws IOException { }

  /**
   * For committing job's output after successful job completion. Note that this
   * is invoked for jobs with final runstate as SUCCESSFUL.	
   * 
   * @param jobContext Context of the job whose output is being written.
   * @throws IOException
   */
  public void commitJob(JobContext jobContext) throws IOException {
    cleanupJob(jobContext);
  }

  
  /**
   * For aborting an unsuccessful job's output. Note that this is invoked for 
   * jobs with final runstate as {@link JobStatus.State#FAILED} or 
   * {@link JobStatus.State#KILLED}.
   *
   * @param jobContext Context of the job whose output is being written.
   * @param state final runstate of the job
   * @throws IOException
   */
  public void abortJob(JobContext jobContext, JobStatus.State state) 
  throws IOException {
    cleanupJob(jobContext);
  }
  
  /**
   * Sets up output for the task.
   * 
   * @param taskContext Context of the task whose output is being written.
   * @throws IOException
   */
  public abstract void setupTask(TaskAttemptContext taskContext)
  throws IOException;
  
  /**
   * Check whether task needs a commit
   * 
   * @param taskContext
   * @return true/false
   * @throws IOException
   */
  public abstract boolean needsTaskCommit(TaskAttemptContext taskContext)
  throws IOException;

  /**
   * To promote the task's temporary output to final output location
   * 
   * The task's output is moved to the job's output directory.
   * 
   * @param taskContext Context of the task whose output is being written.
   * @throws IOException if commit is not 
   */
  public abstract void commitTask(TaskAttemptContext taskContext)
  throws IOException;
  
  /**
   * Discard the task output
   * 
   * @param taskContext
   * @throws IOException
   */
  public abstract void abortTask(TaskAttemptContext taskContext)
  throws IOException;
}
