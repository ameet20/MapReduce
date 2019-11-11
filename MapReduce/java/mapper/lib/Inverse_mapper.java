package org.apache.hadoop.mapred.lib;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/** A {@link Mapper} that swaps keys and values. 
 * @deprecated Use {@link org.apache.hadoop.mapreduce.lib.map.InverseMapper} 
 *   instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class InverseMapper<K, V>
    extends MapReduceBase implements Mapper<K, V, V, K> {

  /** The inverse function.  Input keys and values are swapped.*/
  public void map(K key, V value,
                  OutputCollector<V, K> output, Reporter reporter)
    throws IOException {
    output.collect(value, key);
  }
  
}
