package org.apache.hadoop.eclipse.dfs;

/**
 * Interface to define content entities in the DFS browser
 */
public interface DFSContent {

  boolean hasChildren();
  
  DFSContent[] getChildren();
  
  void refresh();
  
}
