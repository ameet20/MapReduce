package org.apache.hadoop.mapreduce.lib.map;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.Mapper;

/** A {@link Mapper} that swaps keys and values. */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class InverseMapper<K, V> extends Mapper<K,V,V,K> {

  /** The inverse function.  Input keys and values are swapped.*/
  @Override
  public void map(K key, V value, Context context
                  ) throws IOException, InterruptedException {
    context.write(value, key);
  }
  
}
