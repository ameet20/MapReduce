package org.apache.hadoop.mapred.lib;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.MapReduceBase;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.LongWritable;

/** A {@link Reducer} that sums long values. 
 * @deprecated Use {@link org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer}
 *    instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class LongSumReducer<K> extends MapReduceBase
    implements Reducer<K, LongWritable, K, LongWritable> {

  public void reduce(K key, Iterator<LongWritable> values,
                     OutputCollector<K, LongWritable> output,
                     Reporter reporter)
    throws IOException {

    // sum all values for this key
    long sum = 0;
    while (values.hasNext()) {
      sum += values.next().get();
    }

    // output sum
    output.collect(key, new LongWritable(sum));
  }

}
