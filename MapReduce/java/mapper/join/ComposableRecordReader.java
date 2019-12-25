package org.apache.hadoop.mapred.join;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.RecordReader;

/**
 * Additional operations required of a RecordReader to participate in a join.
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.join.ComposableRecordReader} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface ComposableRecordReader<K extends WritableComparable,
                                 V extends Writable>
    extends RecordReader<K,V>, Comparable<ComposableRecordReader<K,?>> {

  /**
   * Return the position in the collector this class occupies.
   */
  int id();

  /**
   * Return the key this RecordReader would supply on a call to next(K,V)
   */
  K key();

  /**
   * Clone the key at the head of this RecordReader into the object provided.
   */
  void key(K key) throws IOException;

  /**
   * Returns true if the stream is not empty, but provides no guarantee that
   * a call to next(K,V) will succeed.
   */
  boolean hasNext();

  /**
   * Skip key-value pairs with keys less than or equal to the key provided.
   */
  void skip(K key) throws IOException;

  /**
   * While key-value pairs from this RecordReader match the given key, register
   * them with the JoinCollector provided.
   */
  void accept(CompositeRecordReader.JoinCollector jc, K key) throws IOException;
}
