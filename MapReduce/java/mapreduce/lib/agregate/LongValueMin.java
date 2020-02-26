package org.apache.hadoop.mapreduce.lib.aggregate;

import java.util.ArrayList;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that maintain the minimum of 
 * a sequence of long values.
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class LongValueMin implements ValueAggregator<String> {

  long minVal = Long.MAX_VALUE;
    
  /**
   *  the default constructor
   *
   */
  public LongValueMin() {
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
    if (this.minVal > newVal) {
      this.minVal = newVal;
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
    if (this.minVal > newVal) {
      this.minVal = newVal;
    };
  }
    
  /**
   * @return the aggregated value
   */
  public long getVal() {
    return this.minVal;
  }
    
  /**
   * @return the string representation of the aggregated value
   */
  public String getReport() {
    return ""+minVal;
  }

  /**
   * reset the aggregator
   */
  public void reset() {
    minVal = Long.MAX_VALUE;
  }

  /**
   * @return return an array of one element. The element is a string
   *         representation of the aggregated value. The return value is
   *         expected to be used by the a combiner.
   */
  public ArrayList<String> getCombinerOutput() {
    ArrayList<String> retv = new ArrayList<String>(1);
    retv.add(""+minVal);
    return retv;
  }
}
