package org.apache.hadoop.mapred.join;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Writable;

/**
 * Writable type storing multiple {@link org.apache.hadoop.io.Writable}s.
 *
 * This is *not* a general-purpose tuple type. In almost all cases, users are
 * encouraged to implement their own serializable types, which can perform
 * better validation and provide more efficient encodings than this class is
 * capable. TupleWritable relies on the join framework for type safety and
 * assumes its instances will rarely be persisted, assumptions not only
 * incompatible with, but contrary to the general case.
 *
 * @see org.apache.hadoop.io.Writable
 * 
 * @deprecated Use 
 * {@link org.apache.hadoop.mapreduce.lib.join.TupleWritable} instead
 */
@Deprecated
@InterfaceAudience.Public
@InterfaceStability.Stable
public class TupleWritable 
    extends org.apache.hadoop.mapreduce.lib.join.TupleWritable {

  /**
   * Create an empty tuple with no allocated storage for writables.
   */
  public TupleWritable() {
    super();
  }

  /**
   * Initialize tuple with storage; unknown whether any of them contain
   * &quot;written&quot; values.
   */
  public TupleWritable(Writable[] vals) {
    super(vals);
  }

  /**
   * Record that the tuple contains an element at the position provided.
   */
  void setWritten(int i) {
    written.set(i);
  }

  /**
   * Record that the tuple does not contain an element at the position
   * provided.
   */
  void clearWritten(int i) {
    written.clear(i);
  }

  /**
   * Clear any record of which writables have been written to, without
   * releasing storage.
   */
  void clearWritten() {
    written.clear();
  }


}
