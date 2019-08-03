package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Collects the <code>&lt;key, value&gt;</code> pairs output by {@link Mapper}s
 * and {@link Reducer}s.
 *  
 * <p><code>OutputCollector</code> is the generalization of the facility 
 * provided by the Map-Reduce framework to collect data output by either the 
 * <code>Mapper</code> or the <code>Reducer</code> i.e. intermediate outputs 
 * or the output of the job.</p>  
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface OutputCollector<K, V> {
  
  /** Adds a key/value pair to the output.
   *
   * @param key the key to collect.
   * @param value to value to collect.
   * @throws IOException
   */
  void collect(K key, V value) throws IOException;
}
