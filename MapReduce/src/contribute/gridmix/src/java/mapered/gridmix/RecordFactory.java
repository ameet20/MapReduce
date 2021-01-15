package org.apache.hadoop.mapred.gridmix;

import java.io.Closeable;
import java.io.IOException;

/**
 * Interface for producing records as inputs and outputs to tasks.
 */
abstract class RecordFactory implements Closeable {

  /**
   * Transform the given record or perform some operation.
   * @return true if the record should be emitted.
   */
  public abstract boolean next(GridmixKey key, GridmixRecord val)
    throws IOException;

  /**
   * Estimate of exhausted record capacity.
   */
  public abstract float getProgress() throws IOException;

}
