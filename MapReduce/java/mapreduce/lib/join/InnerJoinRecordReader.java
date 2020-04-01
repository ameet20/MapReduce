package org.apache.hadoop.mapreduce.lib.join;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Full inner join.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class InnerJoinRecordReader<K extends WritableComparable<?>>
    extends JoinRecordReader<K> {

  InnerJoinRecordReader(int id, Configuration conf, int capacity,
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
