package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Used when file type differs from the desired file type. like 
 * getting a file when a directory is expected. Or a wrong file type. 
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class InvalidFileTypeException
    extends IOException {

  private static final long serialVersionUID = 1L;

  public InvalidFileTypeException() {
    super();
  }

  public InvalidFileTypeException(String msg) {
    super(msg);
  }

}
