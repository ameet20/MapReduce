package org.apache.hadoop.mapred.join;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.JobConf;

/**
 * Full outer join.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.join.OuterJoinRecordReader} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class OuterJoinRecordReader<K extends WritableComparable>
    extends JoinRecordReader<K> {

  OuterJoinRecordReader(int id, JobConf conf, int capacity,
      Class<? extends WritableComparator> cmpcl) throws IOException {
    super(id, conf, capacity, cmpcl);
  }

  /**
   * Emit everything from the collector.
   */
  protected boolean combine(Object[] srcs, TupleWritable dst) {
    assert srcs.length == dst.size();
    return true;
  }
}
