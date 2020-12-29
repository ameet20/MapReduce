package org.apache.hadoop.mapred;

/**
 * Thrown when the allocation file for {@link PoolManager} is malformed.  
 */
public class AllocationConfigurationException extends Exception {
  private static final long serialVersionUID = 4046517047810854249L;
  
  public AllocationConfigurationException(String message) {
    super(message);
  }
}
