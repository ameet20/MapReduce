package org.apache.hadoop.mapreduce.lib.aggregate;

import java.util.ArrayList;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that maintain the maximum of 
 * a sequence of long values.
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class LongValueMax implements ValueAggregator<String> {

  long maxVal = Long.MIN_VALUE;
    
  /**
   *  the default constructor
   *
   */
  public LongValueMax() {
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
    long newVal = Long.parseLong(val.toString());
    if (this.maxVal < newVal) {
      this.maxVal = newVal;
    }
  }
    
  /**
   * add a value to the aggregator
   * 
   * @param newVal
   *          a long value.
   * 
   */
  public void addNextValue(long newVal) {
    if (this.maxVal < newVal) {
      this.maxVal = newVal;
    };
  }
    
  /**
   * @return the aggregated value
   */
  public long getVal() {
    return this.maxVal;
  }
    
  /**
   * @return the string representation of the aggregated value
   */
  public String getReport() {
    return ""+maxVal;
  }

  /**
   * reset the aggregator
   */
  public void reset() {
    maxVal = Long.MIN_VALUE;
  }

  /**
   * @return return an array of one element. The element is a string
   *         representation of the aggregated value. The return value is
   *         expected to be used by the a combiner.
   */
  public ArrayList<String> getCombinerOutput() {
    ArrayList<String> retv = new ArrayList<String>(1);;
    retv.add("" + maxVal);
    return retv;
  }
}
