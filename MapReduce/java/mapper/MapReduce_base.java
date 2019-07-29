package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Closeable;
import org.apache.hadoop.mapred.JobConfigurable;

/** 
 * Base class for {@link Mapper} and {@link Reducer} implementations.
 * 
 * <p>Provides default no-op implementations for a few methods, most non-trivial
 * applications need to override some of them.</p>
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class MapReduceBase implements Closeable, JobConfigurable {

  /** Default implementation that does nothing. */
  public void close() throws IOException {
  }

  /** Default implementation that does nothing. */
  public void configure(JobConf job) {
  }

}
