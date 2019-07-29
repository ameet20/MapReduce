package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.util.ReflectionUtils;

/** Default {@link MapRunnable} implementation.*/
@InterfaceAudience.Public
@InterfaceStability.Stable
public class MapRunner<K1, V1, K2, V2>
    implements MapRunnable<K1, V1, K2, V2> {
  
  private Mapper<K1, V1, K2, V2> mapper;
  private boolean incrProcCount;

  @SuppressWarnings("unchecked")
  public void configure(JobConf job) {
    this.mapper = ReflectionUtils.newInstance(job.getMapperClass(), job);
    //increment processed counter only if skipping feature is enabled
    this.incrProcCount = SkipBadRecords.getMapperMaxSkipRecords(job)>0 && 
      SkipBadRecords.getAutoIncrMapperProcCount(job);
  }

  public void run(RecordReader<K1, V1> input, OutputCollector<K2, V2> output,
                  Reporter reporter)
    throws IOException {
    try {
      // allocate key & value instances that are re-used for all entries
      K1 key = input.createKey();
      V1 value = input.createValue();
      
      while (input.next(key, value)) {
        // map pair to output
        mapper.map(key, value, output, reporter);
        if(incrProcCount) {
          reporter.incrCounter(SkipBadRecords.COUNTER_GROUP, 
              SkipBadRecords.COUNTER_MAP_PROCESSED_RECORDS, 1);
        }
      }
    } finally {
      mapper.close();
    }
  }

  protected Mapper<K1, V1, K2, V2> getMapper() {
    return mapper;
  }
}
