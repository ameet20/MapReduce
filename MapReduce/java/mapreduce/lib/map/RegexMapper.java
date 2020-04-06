package org.apache.hadoop.mapreduce.lib.map;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


/** A {@link Mapper} that extracts text matching a regular expression. */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class RegexMapper<K> extends Mapper<K, Text, Text, LongWritable> {

  public static String PATTERN = "mapreduce.mapper.regex";
  public static String GROUP = "mapreduce.mapper.regexmapper..group";
  private Pattern pattern;
  private int group;

  public void setup(Context context) {
    Configuration conf = context.getConfiguration();
    pattern = Pattern.compile(conf.get(PATTERN));
    group = conf.getInt(GROUP, 0);
  }

  public void map(K key, Text value,
                  Context context)
    throws IOException, InterruptedException {
    String text = value.toString();
    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      context.write(new Text(matcher.group(group)), new LongWritable(1));
    }
  }
}
