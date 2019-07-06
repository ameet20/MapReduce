package org.apache.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class JvmContext implements Writable {

  public static final Log LOG =
    LogFactory.getLog(JvmContext.class);
  
  JVMId jvmId;
  String pid;
  
  JvmContext() {
    jvmId = new JVMId();
    pid = "";
  }
  
  JvmContext(JVMId id, String pid) {
    jvmId = id;
    this.pid = pid;
  }
  
  public void readFields(DataInput in) throws IOException {
    jvmId.readFields(in);
    this.pid = Text.readString(in);
  }
  
  public void write(DataOutput out) throws IOException {
    jvmId.write(out);
    Text.writeString(out, pid);
  }
}
