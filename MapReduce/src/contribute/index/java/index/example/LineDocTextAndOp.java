package org.apache.hadoop.contrib.index.example;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.contrib.index.mapred.DocumentAndOp;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * This class represents an operation. The operation can be an insert, a delete
 * or an update. If the operation is an insert or an update, a (new) document,
 * which is in the form of text, is specified.
 */
public class LineDocTextAndOp implements Writable {
  private DocumentAndOp.Op op;
  private Text doc;

  /**
   * Constructor
   */
  public LineDocTextAndOp() {
    doc = new Text();
  }

  /**
   * Set the type of the operation.
   * @param op  the type of the operation
   */
  public void setOp(DocumentAndOp.Op op) {
    this.op = op;
  }

  /**
   * Get the type of the operation.
   * @return the type of the operation
   */
  public DocumentAndOp.Op getOp() {
    return op;
  }

  /**
   * Get the text that represents a document.
   * @return the text that represents a document
   */
  public Text getText() {
    return doc;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return this.getClass().getName() + "[op=" + op + ", text=" + doc + "]";
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
