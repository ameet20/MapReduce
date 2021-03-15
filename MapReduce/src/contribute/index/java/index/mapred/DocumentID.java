package org.apache.hadoop.contrib.index.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 * The class represents a document id, which is of type text.
 */
public class DocumentID implements WritableComparable {
  private final Text docID;

  /**
   * Constructor.
   */
  public DocumentID() {
    docID = new Text();
  }

  /**
   * The text of the document id.
   * @return the text
   */
  public Text getText() {
    return docID;
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object obj) {
    if (this == obj) {
      return 0;
    } else {
      return docID.compareTo(((DocumentID) obj).docID);
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return docID.hashCode();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return this.getClass().getName() + "[" + docID + "]";
  }

  /* (non-Javadoc)
   * @see org.apache.hadoop.io.Writable#write(java.io.DataOutput)
   */
  public void write(DataOutput out) throws IOException {
    throw new IOException(this.getClass().getName()
        + ".write should never be called");
  }

  /* (non-Javadoc)
   * @see org.apache.hadoop.io.Writable#readFields(java.io.DataInput)
   */
  public void readFields(DataInput in) throws IOException {
    throw new IOException(this.getClass().getName()
        + ".readFields should never be called");
  }
}
