package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Expert: Generic interface for {@link Mapper}s.
 * 
 * <p>Custom implementations of <code>MapRunnable</code> can exert greater 
 * control on map processing e.g. multi-threaded, asynchronous mappers etc.</p>
 * 
 * @see Mapper
 * @deprecated Use {@link org.apache.hadoop.mapreduce.Mapper} instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface MapRunnable<K1, V1, K2, V2>
    extends JobConfigurable {
  
  /** 
   * Start mapping input <tt>&lt;key, value&gt;</tt> pairs.
   *  
   * <p>Mapping of input records to output records is complete when this method 
   * returns.</p>
   * 
   * @param input the {@link RecordReader} to read the input records.
   * @param output the {@link OutputCollector} to collect the outputrecords.
   * @param reporter {@link Reporter} to report progress, status-updates etc.
   * @throws IOException
   */
  void run(RecordReader<K1, V1> input, OutputCollector<K2, V2> output,
           Reporter reporter)
    throws IOException;
}
