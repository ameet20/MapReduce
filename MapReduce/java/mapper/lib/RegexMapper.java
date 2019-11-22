package org.apache.hadoop.mapred.lib;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


/** A {@link Mapper} that extracts text matching a regular expression.
 * @deprecated Use {@link org.apache.hadoop.mapreduce.lib.map.RegexMapper}
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class RegexMapper<K> extends MapReduceBase
    implements Mapper<K, Text, Text, LongWritable> {

  private Pattern pattern;
  private int group;

  public void configure(JobConf job) {
    pattern = Pattern.compile(job.get(org.apache.hadoop.mapreduce.lib.map.
                RegexMapper.PATTERN));
    group = job.getInt(org.apache.hadoop.mapreduce.lib.map.
              RegexMapper.GROUP, 0);
  }

  public void map(K key, Text value,
                  OutputCollector<Text, LongWritable> output,
                  Reporter reporter)
    throws IOException {
    String text = value.toString();
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      output.collect(new Text(matcher.group(group)), new LongWritable(1));
    }
  }

}
