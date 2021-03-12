package org.apache.hadoop.contrib.index.example;

import java.io.IOException;

import org.apache.hadoop.contrib.index.mapred.DocumentAndOp;
import org.apache.hadoop.contrib.index.mapred.DocumentID;
import org.apache.hadoop.contrib.index.mapred.ILocalAnalysis;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * Identity local analysis maps inputs directly into outputs.
 */
public class IdentityLocalAnalysis implements
    ILocalAnalysis<DocumentID, DocumentAndOp> {

  /* (non-Javadoc)
   * @see org.apache.hadoop.mapred.Mapper#map(java.lang.Object, java.lang.Object, org.apache.hadoop.mapred.OutputCollector, org.apache.hadoop.mapred.Reporter)
   */
  public void map(DocumentID key, DocumentAndOp value,
      OutputCollector<DocumentID, DocumentAndOp> output, Reporter reporter)
      throws IOException {
    output.collect(key, value);
  }

  /* (non-Javadoc)
   * @see org.apache.hadoop.mapred.JobConfigurable#configure(org.apache.hadoop.mapred.JobConf)
   */
  public void configure(JobConf job) {
  }

  /* (non-Javadoc)
   * @see org.apache.hadoop.io.Closeable#close()
   */
  public void close() throws IOException {
  }

}
