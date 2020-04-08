package org.apache.hadoop.mapreduce.task.reduce;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.BoundedByteArrayOutputStream;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.mapred.IFile;
import org.apache.hadoop.mapred.IFileOutputStream;
import org.apache.hadoop.mapred.IFile.Writer;

@InterfaceAudience.Private
@InterfaceStability.Unstable
public class InMemoryWriter<K, V> extends Writer<K, V> {
  private DataOutputStream out;
  
  public InMemoryWriter(BoundedByteArrayOutputStream arrayStream) {
    super(null);
    this.out = 
      new DataOutputStream(new IFileOutputStream(arrayStream));
  }
  
  public void append(K key, V value) throws IOException {
    throw new UnsupportedOperationException
    ("InMemoryWriter.append(K key, V value");
  }
  
  public void append(DataInputBuffer key, DataInputBuffer value)
  throws IOException {
    int keyLength = key.getLength() - key.getPosition();
    if (keyLength < 0) {
      throw new IOException("Negative key-length not allowed: " + keyLength + 
                            " for " + key);
    }
    
    int valueLength = value.getLength() - value.getPosition();
    if (valueLength < 0) {
      throw new IOException("Negative value-length not allowed: " + 
                            valueLength + " for " + value);
    }
    WritableUtils.writeVInt(out, keyLength);
    WritableUtils.writeVInt(out, valueLength);
    out.write(key.getData(), key.getPosition(), keyLength); 
    out.write(value.getData(), value.getPosition(), valueLength); 
  }
  public void close() throws IOException {
    // Write EOF_MARKER for key/value length
    WritableUtils.writeVInt(out, IFile.EOF_MARKER);
    WritableUtils.writeVInt(out, IFile.EOF_MARKER);
    
    // Close the stream 
    out.close();
    out = null;
  }
}
