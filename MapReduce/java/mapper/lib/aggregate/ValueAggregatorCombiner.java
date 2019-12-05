package org.apache.hadoop.mapred.lib.aggregate;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * This class implements the generic combiner of Aggregate.
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.aggregate.ValueAggregatorCombiner}
 * instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class ValueAggregatorCombiner<K1 extends WritableComparable,
                                     V1 extends Writable>
  extends ValueAggregatorJobBase<K1, V1> {

  /**
   * Combiner does not need to configure.
   */
  public void configure(JobConf job) {

  }

  /** Combines values for a given key.  
   * @param key the key is expected to be a Text object, whose prefix indicates
   * the type of aggregation to aggregate the values. 
   * @param values the values to combine
   * @param output to collect combined values
   */
  public void reduce(Text key, Iterator<Text> values,
                     OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
    String keyStr = key.toString();
    int pos = keyStr.indexOf(ValueAggregatorDescriptor.TYPE_SEPARATOR);
    String type = keyStr.substring(0, pos);
    ValueAggregator aggregator = ValueAggregatorBaseDescriptor
      .generateValueAggregator(type);
    while (values.hasNext()) {
      aggregator.addNextValue(values.next());
    }
    Iterator outputs = aggregator.getCombinerOutput().iterator();

    while (outputs.hasNext()) {
      Object v = outputs.next();
      if (v instanceof Text) {
        output.collect(key, (Text)v);
      } else {
        output.collect(key, new Text(v.toString()));
      }
    }
  }

  /** 
   * Do nothing. 
   *
   */
  public void close() throws IOException {

  }

  /** 
   * Do nothing. Should not be called. 
   *
   */
  public void map(K1 arg0, V1 arg1, OutputCollector<Text, Text> arg2,
                  Reporter arg3) throws IOException {
    throw new IOException ("should not be called\n");
  }
}
