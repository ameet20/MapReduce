package org.apache.hadoop.contrib.index.mapred;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.Mapper;

/**
 * Application specific local analysis. The output type must be (DocumentID,
 * DocumentAndOp).
 */
public interface ILocalAnalysis<K extends WritableComparable, V extends Writable>
    extends Mapper<K, V, DocumentID, DocumentAndOp> {

}
