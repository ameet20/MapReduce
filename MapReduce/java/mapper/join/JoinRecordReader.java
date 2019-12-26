package org.apache.hadoop.mapred.join;

import java.io.IOException;
import java.util.PriorityQueue;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.mapred.JobConf;

/**
 * Base class for Composite joins returning Tuples of arbitrary Writables.
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.join.JoinRecordReader} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class JoinRecordReader<K extends WritableComparable>
    extends CompositeRecordReader<K,Writable,TupleWritable>
    implements ComposableRecordReader<K,TupleWritable> {

  public JoinRecordReader(int id, JobConf conf, int capacity,
      Class<? extends WritableComparator> cmpcl) throws IOException {
    super(id, capacity, cmpcl);
    setConf(conf);
  }

  /**
   * Emit the next set of key, value pairs as defined by the child
   * RecordReaders and operation associated with this composite RR.
   */
  public boolean next(K key, TupleWritable value) throws IOException {
    if (jc.flush(value)) {
      WritableUtils.cloneInto(key, jc.key());
      return true;
    }
    jc.clear();
    K iterkey = createKey();
    final PriorityQueue<ComposableRecordReader<K,?>> q = getRecordReaderQueue();
    while (!q.isEmpty()) {
      fillJoinCollector(iterkey);
      jc.reset(iterkey);
      if (jc.flush(value)) {
        WritableUtils.cloneInto(key, jc.key());
        return true;
      }
      jc.clear();
    }
    return false;
  }

  /** {@inheritDoc} */
  public TupleWritable createValue() {
    return createInternalValue();
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
