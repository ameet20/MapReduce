package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;

/**
 * <code>InputSplit</code> represents the data to be processed by an 
 * individual {@link Mapper}. 
 *
 * <p>Typically, it presents a byte-oriented view on the input and is the 
 * responsibility of {@link RecordReader} of the job to process this and present
 * a record-oriented view.
 * 
 * @see InputFormat
 * @see RecordReader
 * @deprecated Use {@link org.apache.hadoop.mapreduce.InputSplit} instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface InputSplit extends Writable {

  /**
   * Get the total number of bytes in the data of the <code>InputSplit</code>.
   * 
   * @return the number of bytes in the input split.
   * @throws IOException
   */
  long getLength() throws IOException;
  
  /**
   * Get the list of hostnames where the input split is located.
   * 
   * @return list of hostnames where data of the <code>InputSplit</code> is
   *         located as an array of <code>String</code>s.
   * @throws IOException
   */
  String[] getLocations() throws IOException;
}
