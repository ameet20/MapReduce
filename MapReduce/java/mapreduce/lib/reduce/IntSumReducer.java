package org.apache.hadoop.mapreduce.lib.reduce;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

@InterfaceAudience.Public
@InterfaceStability.Stable
public class IntSumReducer<Key> extends Reducer<Key,IntWritable,
                                                Key,IntWritable> {
  private IntWritable result = new IntWritable();

  public void reduce(Key key, Iterable<IntWritable> values, 
                     Context context) throws IOException, InterruptedException {
    int sum = 0;
    for (IntWritable val : values) {
      sum += val.get();
    }
    result.set(sum);
    context.write(key, result);
  }

}
