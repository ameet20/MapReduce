package org.apache.hadoop.mapreduce.lib.aggregate;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * This class implements the generic combiner of Aggregate.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class ValueAggregatorCombiner<K1 extends WritableComparable<?>,
                                     V1 extends Writable>
  extends Reducer<Text, Text, Text, Text> {

  /** Combines values for a given key.  
   * @param key the key is expected to be a Text object, whose prefix indicates
   * the type of aggregation to aggregate the values. 
   * @param values the values to combine
   * @param context to collect combined values
   */
  public void reduce(Text key, Iterable<Text> values, Context context) 
      throws IOException, InterruptedException {
    String keyStr = key.toString();
    int pos = keyStr.indexOf(ValueAggregatorDescriptor.TYPE_SEPARATOR);
    String type = keyStr.substring(0, pos);
    long uniqCount = context.getConfiguration().
      getLong(UniqValueCount.MAX_NUM_UNIQUE_VALUES, Long.MAX_VALUE);
    ValueAggregator aggregator = ValueAggregatorBaseDescriptor
      .generateValueAggregator(type, uniqCount);
    for (Text val : values) {
      aggregator.addNextValue(val);
    }
    Iterator<?> outputs = aggregator.getCombinerOutput().iterator();

    while (outputs.hasNext()) {
      Object v = outputs.next();
      if (v instanceof Text) {
        context.write(key, (Text)v);
      } else {
        context.write(key, new Text(v.toString()));
      }
    }
  }
}
