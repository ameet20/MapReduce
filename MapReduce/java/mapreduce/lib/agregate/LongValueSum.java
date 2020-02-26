package org.apache.hadoop.mapreduce.lib.aggregate;

import java.util.ArrayList;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that sums up 
 * a sequence of long values.
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class LongValueSum implements ValueAggregator<String> {

  long sum = 0;
    
  /**
   *  the default constructor
   *
   */
  public LongValueSum() {
    reset();
  }

  /**
   * add a value to the aggregator
   * 
   * @param val
   *          an object whose string representation represents a long value.
   * 
   */
  public void addNextValue(Object val) {
    this.sum += Long.parseLong(val.toString());
  }
    
  /**
   * add a value to the aggregator
   * 
   * @param val
   *          a long value.
   * 
   */
  public void addNextValue(long val) {
    this.sum += val;
  }
    
  /**
   * @return the aggregated value
   */
  public long getSum() {
    return this.sum;
  }
    
  /**
   * @return the string representation of the aggregated value
   */
  public String getReport() {
    return ""+sum;
  }

  /**
   * reset the aggregator
   */
  public void reset() {
    sum = 0;
  }

  /**
   * @return return an array of one element. The element is a string
   *         representation of the aggregated value. The return value is
   *         expected to be used by the a combiner.
   */
  public ArrayList<String> getCombinerOutput() {
    ArrayList<String> retv = new ArrayList<String>(1);
    retv.add(""+sum);
    return retv;
  }
}

