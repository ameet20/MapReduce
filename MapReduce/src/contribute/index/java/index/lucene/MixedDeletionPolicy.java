package org.apache.hadoop.contrib.index.lucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.IndexCommitPoint;
import org.apache.lucene.index.IndexDeletionPolicy;

/**
 * For mixed directory. Use KeepAllDeletionPolicy for the read-only directory
 * (keep all from init) and use KeepOnlyLastCommitDeletionPolicy for the
 * writable directory (initially empty, keep latest after init).
 */
class MixedDeletionPolicy implements IndexDeletionPolicy {

  private int keepAllFromInit = 0;

  public void onInit(List commits) throws IOException {
    keepAllFromInit = commits.size();
  }

  public void onCommit(List commits) throws IOException {
    int size = commits.size();
    assert (size > keepAllFromInit);
    // keep all from init and the latest, delete the rest
    for (int i = keepAllFromInit; i < size - 1; i++) {
      ((IndexCommitPoint) commits.get(i)).delete();
    }
  }

}
