package org.apache.hadoop.mapreduce.lib.join;

import java.io.IOException;
import java.util.PriorityQueue;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * Base class for Composite joins returning Tuples of arbitrary Writables.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class JoinRecordReader<K extends WritableComparable<?>>
    extends CompositeRecordReader<K,Writable,TupleWritable> {

  public JoinRecordReader(int id, Configuration conf, int capacity,
      Class<? extends WritableComparator> cmpcl) throws IOException {
    super(id, capacity, cmpcl);
    setConf(conf);
  }

  /**
   * Emit the next set of key, value pairs as defined by the child
   * RecordReaders and operation associated with this composite RR.
   */
  public boolean nextKeyValue() 
      throws IOException, InterruptedException {
    if (key == null) {
      key = createKey();
    }
    if (jc.flush(value)) {
      ReflectionUtils.copy(conf, jc.key(), key);
      return true;
    }
    jc.clear();
    if (value == null) {
      value = createValue();
    }
    final PriorityQueue<ComposableRecordReader<K,?>> q = 
            getRecordReaderQueue();
    K iterkey = createKey();
    while (q != null && !q.isEmpty()) {
      fillJoinCollector(iterkey);
      jc.reset(iterkey);
      if (jc.flush(value)) {
        ReflectionUtils.copy(conf, jc.key(), key);
        return true;
      }
      jc.clear();
    }
    return false;
  }

  public TupleWritable createValue() {
    return createTupleWritable();
  }

  /**
   * Return an iterator wrapping the JoinCollector.
   */
  protected ResetableIterator<TupleWritable> getDelegate() {
    return new JoinDelegationIterator();
  }

  /**
   * Since the JoinCollector is effecting our operation, we need only
   * provide an iterator proxy wrapping its operation.
   */
  protected class JoinDelegationIterator
      implements ResetableIterator<TupleWritable> {

    public boolean hasNext() {
      return jc.hasNext();
    }

    public boolean next(TupleWritable val) throws IOException {
      return jc.flush(val);
    }

    public boolean replay(TupleWritable val) throws IOException {
      return jc.replay(val);
    }

    public void reset() {
      jc.reset(jc.key());
    }

    public void add(TupleWritable item) throws IOException {
      throw new UnsupportedOperationException();
    }

    public void close() throws IOException {
      jc.close();
    }

    public void clear() {
      jc.clear();
    }
  }
}
