package org.apache.hadoop.eclipse.dfs;

import java.io.IOException;

import org.apache.hadoop.eclipse.server.HadoopServer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * DFS Content representation of a HDFS location
 */
public class DFSLocation implements DFSContent {

  private final DFSContentProvider provider;

  private final HadoopServer location;

  private DFSContent rootFolder = null;

  DFSLocation(DFSContentProvider provider, HadoopServer server) {
    this.provider = provider;
    this.location = server;
  }

  /* @inheritDoc */
  @Override
  public String toString() {
    return this.location.getLocationName();
  }

  /*
   * Implementation of DFSContent
   */

  /* @inheritDoc */
  public DFSContent[] getChildren() {
    if (this.rootFolder == null) {
      /*
       * DfsFolder constructor might block as it contacts the NameNode: work
       * asynchronously here or this will potentially freeze the UI
       */
      new Job("Connecting to DFS " + location) {
        @Override
        protected IStatus run(IProgressMonitor monitor) {
          try {
            rootFolder = new DFSFolder(provider, location);
            return Status.OK_STATUS;

          } catch (IOException ioe) {
            rootFolder =
                new DFSMessage("Error: " + ioe.getLocalizedMessage());
            return Status.CANCEL_STATUS;

          } finally {
            // Under all circumstances, update the UI
            provider.refresh(DFSLocation.this);
          }
        }
      }.schedule();

      return new DFSContent[] { new DFSMessage("Connecting to DFS "
          + toString()) };
    }
    return new DFSContent[] { this.rootFolder };
  }

  /* @inheritDoc */
  public boolean hasChildren() {
    return true;
  }
  
  /* @inheritDoc */
  public void refresh() {
    this.rootFolder = null;
    this.provider.refresh(this);
  }

  /*
   * Actions
   */
  
  /**
   * Refresh the location using a new connection
   */
  public void reconnect() {
    this.refresh();
  }
}
