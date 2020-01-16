package org.apache.hadoop.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.WritableComparable;

/**
 * A general identifier, which internally stores the id
 * as an integer. This is the super class of {@link JobID}, 
 * {@link TaskID} and {@link TaskAttemptID}.
 * 
 * @see JobID
 * @see TaskID
 * @see TaskAttemptID
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class ID implements WritableComparable<ID> {
  protected static final char SEPARATOR = '_';
  protected int id;

  /** constructs an ID object from the given int */
  public ID(int id) {
    this.id = id;
  }

  protected ID() {
  }

  /** returns the int which represents the identifier */
  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return String.valueOf(id);
  }

  @Override
  public int hashCode() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if(o == null)
      return false;
    if (o.getClass() == this.getClass()) {
      ID that = (ID) o;
      return this.id == that.id;
    }
    else
      return false;
  }

  /** Compare IDs by associated numbers */
  public int compareTo(ID that) {
    return this.id - that.id;
  }

  public void readFields(DataInput in) throws IOException {
    this.id = in.readInt();
  }

  public void write(DataOutput out) throws IOException {
    out.writeInt(id);
  }
  
}
