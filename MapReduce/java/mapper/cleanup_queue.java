package org.apache.hadoop.mapred;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

class CleanupQueue {

  public static final Log LOG =
    LogFactory.getLog(CleanupQueue.class);

  private static PathCleanupThread cleanupThread;

  /**
   * Create a singleton path-clean-up queue. It can be used to delete
   * paths(directories/files) in a separate thread. This constructor creates a
   * clean-up thread and also starts it as a daemon. Callers can instantiate one
   * CleanupQueue per JVM and can use it for deleting paths. Use
   * {@link CleanupQueue#addToQueue(PathDeletionContext...)} to add paths for
   * deletion.
   */
  public CleanupQueue() {
    synchronized (PathCleanupThread.class) {
      if (cleanupThread == null) {
        cleanupThread = new PathCleanupThread();
      }
    }
  }
  
  /**
   * Contains info related to the path of the file/dir to be deleted
   */
  static class PathDeletionContext {
    String fullPath;// full path of file or dir
    FileSystem fs;

    public PathDeletionContext(FileSystem fs, String fullPath) {
      this.fs = fs;
      this.fullPath = fullPath;
    }
    
    protected String getPathForCleanup() {
      return fullPath;
    }

    /**
     * Makes the path(and its subdirectories recursively) fully deletable
     */
    protected void enablePathForCleanup() throws IOException {
      // Do nothing by default.
      // Subclasses can override to provide enabling for deletion.
    }
  }

  /**
   * Adds the paths to the queue of paths to be deleted by cleanupThread.
   */
  void addToQueue(PathDeletionContext... contexts) {
    cleanupThread.addToQueue(contexts);
  }

  protected static boolean deletePath(PathDeletionContext context)
            throws IOException {
    context.enablePathForCleanup();

    if (LOG.isDebugEnabled()) {
      LOG.debug("Trying to delete " + context.fullPath);
    }
    if (context.fs.exists(new Path(context.fullPath))) {
      return context.fs.delete(new Path(context.fullPath), true);
    }
    return true;
  }

  // currently used by tests only
  protected boolean isQueueEmpty() {
    return (cleanupThread.queue.size() == 0);
  }

  private static class PathCleanupThread extends Thread {

    // cleanup queue which deletes files/directories of the paths queued up.
    private LinkedBlockingQueue<PathDeletionContext> queue =
      new LinkedBlockingQueue<PathDeletionContext>();

    public PathCleanupThread() {
      setName("Directory/File cleanup thread");
      setDaemon(true);
      start();
    }

    void addToQueue(PathDeletionContext[] contexts) {
      for (PathDeletionContext context : contexts) {
        try {
          queue.put(context);
        } catch(InterruptedException ie) {}
      }
    }

    public void run() {
      if (LOG.isDebugEnabled()) {
        LOG.debug(getName() + " started.");
      }
      PathDeletionContext context = null;
      while (true) {
        try {
          context = queue.take();
          // delete the path.
          if (!deletePath(context)) {
            LOG.warn("CleanupThread:Unable to delete path " + context.fullPath);
          }
          else if (LOG.isDebugEnabled()) {
            LOG.debug("DELETED " + context.fullPath);
          }
        } catch (InterruptedException t) {
          LOG.warn("Interrupted deletion of " + context.fullPath);
          return;
        } catch (Exception e) {
          LOG.warn("Error deleting path " + context.fullPath + ": " + e);
        } 
      }
    }
  }
}
