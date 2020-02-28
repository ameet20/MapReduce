package org.apache.hadoop.mapreduce.lib.aggregate;

import java.util.ArrayList;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This interface defines the minimal protocol for value aggregators.
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface ValueAggregator<E> {

  /**
   * add a value to the aggregator
   * 
   * @param val the value to be added
   */
  public void addNextValue(Object val);

  /**
   * reset the aggregator
   *
   */
  public void reset();

  /**
   * @return the string representation of the agregator
   */
  public String getReport();

  /**
   * 
   * @return an array of values as the outputs of the combiner.
   */
  public ArrayList<E> getCombinerOutput();

}
