package org.apache.hadoop.mapreduce.lib.map;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Tokenize the input values and emit each word with a count of 1.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class TokenCounterMapper extends Mapper<Object, Text, Text, IntWritable>{
    
  private final static IntWritable one = new IntWritable(1);
  private Text word = new Text();
  
  @Override
  public void map(Object key, Text value, Context context
                  ) throws IOException, InterruptedException {
    StringTokenizer itr = new StringTokenizer(value.toString());
    while (itr.hasMoreTokens()) {
      word.set(itr.nextToken());
      context.write(word, one);
    }
  }
}
