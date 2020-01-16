package org.apache.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.RecordReader;

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
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class InputSplit {
  /**
   * Get the size of the split, so that the input splits can be sorted by size.
   * @return the number of bytes in the split
   * @throws IOException
   * @throws InterruptedException
   */
  public abstract long getLength() throws IOException, InterruptedException;

  /**
   * Get the list of nodes by name where the data for the split would be local.
   * The locations do not need to be serialized.
   * @return a new array of the node nodes.
   * @throws IOException
   * @throws InterruptedException
   */
  public abstract 
    String[] getLocations() throws IOException, InterruptedException;
}
