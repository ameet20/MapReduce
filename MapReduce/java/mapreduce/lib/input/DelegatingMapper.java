package org.apache.hadoop.mapreduce.lib.input;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * An {@link Mapper} that delegates behavior of paths to multiple other
 * mappers.
 * 
 * @see MultipleInputs#addInputPath(Job, Path, Class, Class)
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class DelegatingMapper<K1, V1, K2, V2> extends Mapper<K1, V1, K2, V2> {

  private Mapper<K1, V1, K2, V2> mapper;

  @SuppressWarnings("unchecked")
  protected void setup(Context context)
      throws IOException, InterruptedException {
    // Find the Mapper from the TaggedInputSplit.
    TaggedInputSplit inputSplit = (TaggedInputSplit) context.getInputSplit();
    mapper = (Mapper<K1, V1, K2, V2>) ReflectionUtils.newInstance(inputSplit
       .getMapperClass(), context.getConfiguration());
    
  }

  @SuppressWarnings("unchecked")
  public void run(Context context) 
      throws IOException, InterruptedException {
    setup(context);
    mapper.run(context);
    cleanup(context);
  }
}
