package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Used when target file already exists for any operation and 
 * is not configured to be overwritten.  
 */
@Deprecated // may be removed after 0.23
@InterfaceAudience.Public
@InterfaceStability.Stable
public class FileAlreadyExistsException
    extends IOException {

  private static final long serialVersionUID = 1L;

  public FileAlreadyExistsException() {
    super();
  }

  public FileAlreadyExistsException(String msg) {
    super(msg);
  }
}
