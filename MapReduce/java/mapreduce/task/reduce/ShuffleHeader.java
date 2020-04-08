package org.apache.hadoop.mapreduce.task.reduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;

/**
 * Shuffle Header information that is sent by the TaskTracker and 
 * deciphered by the Fetcher thread of Reduce task
 *
 */
@InterfaceAudience.Private
@InterfaceStability.Stable
public class ShuffleHeader implements Writable {

  /**
   * The longest possible length of task attempt id that we will accept.
   */
  private static final int MAX_ID_LENGTH = 1000;

  String mapId;
  long uncompressedLength;
  long compressedLength;
  int forReduce;
  
  public ShuffleHeader() { }
  
  public ShuffleHeader(String mapId, long compressedLength,
      long uncompressedLength, int forReduce) {
    this.mapId = mapId;
    this.compressedLength = compressedLength;
    this.uncompressedLength = uncompressedLength;
    this.forReduce = forReduce;
  }
  
  public void readFields(DataInput in) throws IOException {
    mapId = WritableUtils.readStringSafely(in, MAX_ID_LENGTH);
    compressedLength = WritableUtils.readVLong(in);
    uncompressedLength = WritableUtils.readVLong(in);
    forReduce = WritableUtils.readVInt(in);
  }

  public void write(DataOutput out) throws IOException {
    Text.writeString(out, mapId);
    WritableUtils.writeVLong(out, compressedLength);
    WritableUtils.writeVLong(out, uncompressedLength);
    WritableUtils.writeVInt(out, forReduce);
  }
}
