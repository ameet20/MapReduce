package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * A general identifier, which internally stores the id
 * as an integer. This is the super class of {@link JobID}, 
 * {@link TaskID} and {@link TaskAttemptID}.
 * 
 * @see JobID
 * @see TaskID
 * @see TaskAttemptID
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class ID extends org.apache.hadoop.mapreduce.ID {

  /** constructs an ID object from the given int */
  public ID(int id) {
    super(id);
  }

  protected ID() {
  }

}
