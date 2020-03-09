package org.apache.hadoop.mapreduce.lib.aggregate;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * This class implements the generic reducer of Aggregate.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class ValueAggregatorReducer<K1 extends WritableComparable<?>,
                                    V1 extends Writable>
  extends Reducer<Text, Text, Text, Text> {

  public void setup(Context context) 
      throws IOException, InterruptedException {
    ValueAggregatorJobBase.setup(context.getConfiguration());
  }

  /**
   * @param key
   *        the key is expected to be a Text object, whose prefix indicates
   *        the type of aggregation to aggregate the values. In effect, data
   *        driven computing is achieved. It is assumed that each aggregator's
   *        getReport method emits appropriate output for the aggregator. This
   *        may be further customized.
   * @param values the values to be aggregated
   * @param context 
   */
  public void reduce(Text key, Iterable<Text> values,
      Context context) throws IOException, InterruptedException {
    String keyStr = key.toString();
    int pos = keyStr.indexOf(ValueAggregatorDescriptor.TYPE_SEPARATOR);
    String type = keyStr.substring(0, pos);
    keyStr = keyStr.substring(pos + 
               ValueAggregatorDescriptor.TYPE_SEPARATOR.length());
    long uniqCount = context.getConfiguration().
      getLong(UniqValueCount.MAX_NUM_UNIQUE_VALUES, Long.MAX_VALUE);
    ValueAggregator aggregator = ValueAggregatorBaseDescriptor
      .generateValueAggregator(type, uniqCount);
    for (Text value : values) {
      aggregator.addNextValue(value);
    }

    String val = aggregator.getReport();
    key = new Text(keyStr);
    context.write(key, new Text(val));
  }
}
