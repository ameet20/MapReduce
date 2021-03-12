package org.apache.hadoop.contrib.index.example;

import java.io.IOException;

import org.apache.hadoop.contrib.index.mapred.DocumentID;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;

/**
 * An InputFormat for LineDoc for plain text files where each line is a doc.
 */
public class LineDocInputFormat extends
    FileInputFormat<DocumentID, LineDocTextAndOp> {

  /* (non-Javadoc)
   * @see org.apache.hadoop.mapred.FileInputFormat#getRecordReader(org.apache.hadoop.mapred.InputSplit, org.apache.hadoop.mapred.JobConf, org.apache.hadoop.mapred.Reporter)
   */
  public RecordReader<DocumentID, LineDocTextAndOp> getRecordReader(
      InputSplit split, JobConf job, Reporter reporter) throws IOException {
    reporter.setStatus(split.toString());
    return new LineDocRecordReader(job, (FileSplit) split);
  }

}
