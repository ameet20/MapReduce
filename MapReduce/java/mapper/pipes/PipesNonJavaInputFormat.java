package org.apache.hadoop.mapred.pipes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * Dummy input format used when non-Java a {@link RecordReader} is used by
 * the Pipes' application.
 *
 * The only useful thing this does is set up the Map-Reduce job to get the
 * {@link PipesDummyRecordReader}, everything else left for the 'actual'
 * InputFormat specified by the user which is given by 
 * <i>mapreduce.pipes.inputformat</i>.
 */
class PipesNonJavaInputFormat 
implements InputFormat<FloatWritable, NullWritable> {

  public RecordReader<FloatWritable, NullWritable> getRecordReader(
      InputSplit genericSplit, JobConf job, Reporter reporter)
      throws IOException {
    return new PipesDummyRecordReader(job, genericSplit);
  }
  
  public InputSplit[] getSplits(JobConf job, int numSplits) throws IOException {
    // Delegate the generation of input splits to the 'original' InputFormat
    return ReflectionUtils.newInstance(
        job.getClass(Submitter.INPUT_FORMAT, 
                     TextInputFormat.class, 
                     InputFormat.class), job).getSplits(job, numSplits);
  }

  /**
   * A dummy {@link org.apache.hadoop.mapred.RecordReader} to help track the
   * progress of Hadoop Pipes' applications when they are using a non-Java
   * <code>RecordReader</code>.
   *
   * The <code>PipesDummyRecordReader</code> is informed of the 'progress' of
   * the task by the {@link OutputHandler#progress(float)} which calls the
   * {@link #next(FloatWritable, NullWritable)} with the progress as the
   * <code>key</code>.
   */
  static class PipesDummyRecordReader implements RecordReader<FloatWritable, NullWritable> {
    float progress = 0.0f;
    
    public PipesDummyRecordReader(Configuration job, InputSplit split)
    throws IOException{
    }

    
    public FloatWritable createKey() {
      return null;
    }

    public NullWritable createValue() {
      return null;
    }

    public synchronized void close() throws IOException {}

    public synchronized long getPos() throws IOException {
      return 0;
    }

    public float getProgress() {
      return progress;
    }

    public synchronized boolean next(FloatWritable key, NullWritable value)
        throws IOException {
      progress = key.get();
      return true;
    }
  }
}
