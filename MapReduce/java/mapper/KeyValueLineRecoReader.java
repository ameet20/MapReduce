package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

/**
 * This class treats a line in the input as a key/value pair separated by a 
 * separator character. The separator can be specified in config file 
 * under the attribute name mapreduce.input.keyvaluelinerecordreader.key.value.separator. The default
 * separator is the tab character ('\t').
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader} 
 * instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class KeyValueLineRecordReader implements RecordReader<Text, Text> {
  
  private final LineRecordReader lineRecordReader;

  private byte separator = (byte) '\t';

  private LongWritable dummyKey;

  private Text innerValue;

  public Class getKeyClass() { return Text.class; }
  
  public Text createKey() {
    return new Text();
  }
  
  public Text createValue() {
    return new Text();
  }

  public KeyValueLineRecordReader(Configuration job, FileSplit split)
    throws IOException {
    
    lineRecordReader = new LineRecordReader(job, split);
    dummyKey = lineRecordReader.createKey();
    innerValue = lineRecordReader.createValue();
    String sepStr = job.get("mapreduce.input.keyvaluelinerecordreader.key.value.separator", "\t");
    this.separator = (byte) sepStr.charAt(0);
  }

  public static int findSeparator(byte[] utf, int start, int length, 
      byte sep) {
    return org.apache.hadoop.mapreduce.lib.input.
      KeyValueLineRecordReader.findSeparator(utf, start, length, sep);
  }

  /** Read key/value pair in a line. */
  public synchronized boolean next(Text key, Text value)
    throws IOException {
    byte[] line = null;
    int lineLen = -1;
    if (lineRecordReader.next(dummyKey, innerValue)) {
      line = innerValue.getBytes();
      lineLen = innerValue.getLength();
    } else {
      return false;
    }
    if (line == null)
      return false;
    int pos = findSeparator(line, 0, lineLen, this.separator);
    org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader.
      setKeyValue(key, value, line, lineLen, pos);
    return true;
  }
  
  public float getProgress() throws IOException {
    return lineRecordReader.getProgress();
  }
  
  public synchronized long getPos() throws IOException {
    return lineRecordReader.getPos();
  }

  public synchronized void close() throws IOException { 
    lineRecordReader.close();
  }
}
