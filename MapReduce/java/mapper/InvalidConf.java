package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * This exception is thrown when jobconf misses some mendatory attributes
 * or value of some attributes is invalid. 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class InvalidJobConfException
    extends IOException {

  private static final long serialVersionUID = 1L;

  public InvalidJobConfException() {
    super();
  }

  public InvalidJobConfException(String msg) {
    super(msg);
  }

}
