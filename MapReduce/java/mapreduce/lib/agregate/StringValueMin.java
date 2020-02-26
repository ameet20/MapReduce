package org.apache.hadoop.mapreduce.lib.aggregate;

import java.util.ArrayList;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This class implements a value aggregator that maintain the smallest of 
 * a sequence of strings.
 * 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class StringValueMin implements ValueAggregator<String> {

  String minVal = null;
    
  /**
   *  the default constructor
   *
   */
  public StringValueMin() {
    reset();
  }

  /**
   * add a value to the aggregator
   * 
   * @param val
   *          a string.
   * 
   */
  public void addNextValue(Object val) {
    String newVal = val.toString();
    if (this.minVal == null || this.minVal.compareTo(newVal) > 0) {
      this.minVal = newVal;
    }
  }
    
    
  /**
   * @return the aggregated value
   */
  public String getVal() {
    return this.minVal;
  }
    
  /**
   * @return the string representation of the aggregated value
   */
  public String getReport() {
    return minVal;
  }

  /**
   * reset the aggregator
   */
  public void reset() {
    minVal = null;
  }

  /**
   * @return return an array of one element. The element is a string
   *         representation of the aggregated value. The return value is
   *         expected to be used by the a combiner.
   */
  public ArrayList<String> getCombinerOutput() {
    ArrayList<String> retv = new ArrayList<String>(1);
    retv.add(minVal);
    return retv;
  }
}
