package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.Progressable;

/** 
 * An {@link OutputFormat} that writes keys, values to 
 * {@link SequenceFile}s in binary(raw) format
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.output.SequenceFileAsBinaryOutputFormat}
 * instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class SequenceFileAsBinaryOutputFormat 
 extends SequenceFileOutputFormat <BytesWritable,BytesWritable> {

  /** 
   * Inner class used for appendRaw
   */
  static protected class WritableValueBytes extends org.apache.hadoop.mapreduce
      .lib.output.SequenceFileAsBinaryOutputFormat.WritableValueBytes {
  }

  /**
   * Set the key class for the {@link SequenceFile}
   * <p>This allows the user to specify the key class to be different 
   * from the actual class ({@link BytesWritable}) used for writing </p>
   * 
   * @param conf the {@link JobConf} to modify
   * @param theClass the SequenceFile output key class.
   */
  static public void setSequenceFileOutputKeyClass(JobConf conf, 
                                                   Class<?> theClass) {
    conf.setClass(org.apache.hadoop.mapreduce.lib.output.
      SequenceFileAsBinaryOutputFormat.KEY_CLASS, theClass, Object.class);
  }

  /**
   * Set the value class for the {@link SequenceFile}
   * <p>This allows the user to specify the value class to be different 
   * from the actual class ({@link BytesWritable}) used for writing </p>
   * 
   * @param conf the {@link JobConf} to modify
   * @param theClass the SequenceFile output key class.
   */
  static public void setSequenceFileOutputValueClass(JobConf conf, 
                                                     Class<?> theClass) {
    conf.setClass(org.apache.hadoop.mapreduce.lib.output.
      SequenceFileAsBinaryOutputFormat.VALUE_CLASS, theClass, Object.class);
  }

  /**
   * Get the key class for the {@link SequenceFile}
   * 
   * @return the key class of the {@link SequenceFile}
   */
  static public Class<? extends WritableComparable> getSequenceFileOutputKeyClass(JobConf conf) { 
    return conf.getClass(org.apache.hadoop.mapreduce.lib.output.
      SequenceFileAsBinaryOutputFormat.KEY_CLASS, 
      conf.getOutputKeyClass().asSubclass(WritableComparable.class),
      WritableComparable.class);
  }

  /**
   * Get the value class for the {@link SequenceFile}
   * 
   * @return the value class of the {@link SequenceFile}
   */
  static public Class<? extends Writable> getSequenceFileOutputValueClass(JobConf conf) { 
    return conf.getClass(org.apache.hadoop.mapreduce.lib.output.
      SequenceFileAsBinaryOutputFormat.VALUE_CLASS, 
      conf.getOutputValueClass().asSubclass(Writable.class), Writable.class);
  }
  
  @Override 
  public RecordWriter <BytesWritable, BytesWritable> 
             getRecordWriter(FileSystem ignored, JobConf job,
                             String name, Progressable progress)
    throws IOException {
    // get the path of the temporary output file 
    Path file = FileOutputFormat.getTaskOutputPath(job, name);
    
    FileSystem fs = file.getFileSystem(job);
    CompressionCodec codec = null;
    CompressionType compressionType = CompressionType.NONE;
    if (getCompressOutput(job)) {
      // find the kind of compression to do
      compressionType = getOutputCompressionType(job);

      // find the right codec
      Class<? extends CompressionCodec> codecClass = getOutputCompressorClass(job,
	  DefaultCodec.class);
      codec = ReflectionUtils.newInstance(codecClass, job);
    }
    final SequenceFile.Writer out = 
      SequenceFile.createWriter(fs, job, file,
                    getSequenceFileOutputKeyClass(job),
                    getSequenceFileOutputValueClass(job),
                    compressionType,
                    codec,
                    progress);

    return new RecordWriter<BytesWritable, BytesWritable>() {
        
        private WritableValueBytes wvaluebytes = new WritableValueBytes();

        public void write(BytesWritable bkey, BytesWritable bvalue)
          throws IOException {

          wvaluebytes.reset(bvalue);
          out.appendRaw(bkey.getBytes(), 0, bkey.getLength(), wvaluebytes);
          wvaluebytes.reset(null);
        }

        public void close(Reporter reporter) throws IOException { 
          out.close();
        }

      };

  }

  @Override 
  public void checkOutputSpecs(FileSystem ignored, JobConf job) 
            throws IOException {
    super.checkOutputSpecs(ignored, job);
    if (getCompressOutput(job) && 
        getOutputCompressionType(job) == CompressionType.RECORD ){
        throw new InvalidJobConfException("SequenceFileAsBinaryOutputFormat "
                    + "doesn't support Record Compression" );
    }

  }

}
