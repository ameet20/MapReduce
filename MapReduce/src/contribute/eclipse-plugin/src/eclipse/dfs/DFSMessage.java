package org.apache.hadoop.eclipse.dfs;

/**
 * DFS Content that displays a message.
 */
class DFSMessage implements DFSContent {

  private String message;

  DFSMessage(String message) {
    this.message = message;
  }

  /* @inheritDoc */
  @Override
  public String toString() {
    return this.message;
  }

  /*
   * Implementation of DFSContent
   */

  /* @inheritDoc */
  public DFSContent[] getChildren() {
    return null;
  }

  /* @inheritDoc */
  public boolean hasChildren() {
    return false;
  }

  /* @inheritDoc */
  public void refresh() {
    // Nothing to do
  }

}
