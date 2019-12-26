package org.apache.hadoop.mapred.join;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.JobConf;

/**
 * Full inner join.
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.join.InnerJoinRecordReader} instead.
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class InnerJoinRecordReader<K extends WritableComparable>
    extends JoinRecordReader<K> {

  InnerJoinRecordReader(int id, JobConf conf, int capacity,
      Class<? extends WritableComparator> cmpcl) throws IOException {
    super(id, conf, capacity, cmpcl);
  }

  /**
   * Return true iff the tuple is full (all data sources contain this key).
   */
  protected boolean combine(Object[] srcs, TupleWritable dst) {
    assert srcs.length == dst.size();
    for (int i = 0; i < srcs.length; ++i) {
      if (!dst.has(i)) {
        return false;
      }
    }
    return true;
  }
}
