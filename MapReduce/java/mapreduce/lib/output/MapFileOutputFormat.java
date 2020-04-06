package org.apache.hadoop.mapreduce.lib.output;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileUtil;

import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;

/** 
 * An {@link org.apache.hadoop.mapreduce.OutputFormat} that writes 
 * {@link MapFile}s.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class MapFileOutputFormat 
    extends FileOutputFormat<WritableComparable<?>, Writable> {

  public RecordWriter<WritableComparable<?>, Writable> getRecordWriter(
      TaskAttemptContext context) throws IOException {
    Configuration conf = context.getConfiguration();
    CompressionCodec codec = null;
    CompressionType compressionType = CompressionType.NONE;
    if (getCompressOutput(context)) {
      // find the kind of compression to do
      compressionType = SequenceFileOutputFormat.getOutputCompressionType(context);

      // find the right codec
      Class<?> codecClass = getOutputCompressorClass(context,
	                          DefaultCodec.class);
      codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, conf);
    }

    Path file = getDefaultWorkFile(context, "");
    FileSystem fs = file.getFileSystem(conf);
    // ignore the progress parameter, since MapFile is local
    final MapFile.Writer out =
      new MapFile.Writer(conf, fs, file.toString(),
        context.getOutputKeyClass().asSubclass(WritableComparable.class),
        context.getOutputValueClass().asSubclass(Writable.class),
        compressionType, codec, context);

    return new RecordWriter<WritableComparable<?>, Writable>() {
        public void write(WritableComparable<?> key, Writable value)
            throws IOException {
          out.append(key, value);
        }

        public void close(TaskAttemptContext context) throws IOException { 
          out.close();
        }
      };
  }

  /** Open the output generated by this format. */
  public static MapFile.Reader[] getReaders(Path dir,
      Configuration conf) throws IOException {
    FileSystem fs = dir.getFileSystem(conf);
    Path[] names = FileUtil.stat2Paths(fs.listStatus(dir));

    // sort names, so that hash partitioning works
    Arrays.sort(names);
    
    MapFile.Reader[] parts = new MapFile.Reader[names.length];
    for (int i = 0; i < names.length; i++) {
      parts[i] = new MapFile.Reader(fs, names[i].toString(), conf);
    }
    return parts;
  }
    
  /** Get an entry from output generated by this class. */
  public static <K extends WritableComparable<?>, V extends Writable>
      Writable getEntry(MapFile.Reader[] readers, 
      Partitioner<K, V> partitioner, K key, V value) throws IOException {
    int part = partitioner.getPartition(key, value, readers.length);
    return readers[part].get(key, value);
  }
}
