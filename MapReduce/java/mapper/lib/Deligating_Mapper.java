package org.apache.hadoop.mapred.lib;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * An {@link Mapper} that delegates behaviour of paths to multiple other
 * mappers.
 * 
 * @see MultipleInputs#addInputPath(JobConf, Path, Class, Class)
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.input.DelegatingMapper} instead
 */
@Deprecated
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class DelegatingMapper<K1, V1, K2, V2> implements Mapper<K1, V1, K2, V2> {

  private JobConf conf;

  private Mapper<K1, V1, K2, V2> mapper;

  @SuppressWarnings("unchecked")
  public void map(K1 key, V1 value, OutputCollector<K2, V2> outputCollector,
      Reporter reporter) throws IOException {

    if (mapper == null) {
      // Find the Mapper from the TaggedInputSplit.
      TaggedInputSplit inputSplit = (TaggedInputSplit) reporter.getInputSplit();
      mapper = (Mapper<K1, V1, K2, V2>) ReflectionUtils.newInstance(inputSplit
         .getMapperClass(), conf);
    }
    mapper.map(key, value, outputCollector, reporter);
  }

  public void configure(JobConf conf) {
    this.conf = conf;
  }

  public void close() throws IOException {
    if (mapper != null) {
      mapper.close();
    }
  }

}
