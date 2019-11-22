package org.apache.hadoop.mapred.lib;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


/** A {@link Mapper} that maps text values into <token,freq> pairs.  Uses
 * {@link StringTokenizer} to break text into tokens. 
 * @deprecated Use 
 *    {@link org.apache.hadoop.mapreduce.lib.map.TokenCounterMapper} instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class TokenCountMapper<K> extends MapReduceBase
    implements Mapper<K, Text, Text, LongWritable> {

  public void map(K key, Text value,
                  OutputCollector<Text, LongWritable> output,
                  Reporter reporter)
    throws IOException {
    // get input text
    String text = value.toString();       // value is line of text

    // tokenize the value
    StringTokenizer st = new StringTokenizer(text);
    while (st.hasMoreTokens()) {
      // output <token,1> pairs
      output.collect(new Text(st.nextToken()), new LongWritable(1));
    }  
  }
  
}
