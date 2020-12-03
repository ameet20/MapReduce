package org.apache.hadoop.contrib.utils.join;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.contrib.utils.join.TaggedMapOutput;

/**
 * This is a subclass of TaggedMapOutput that is used to
 * demonstrate the functionality of INNER JOIN between 2 data
 * sources (TAB separated text files) based on the first column.
 */
public class SampleTaggedMapOutput extends TaggedMapOutput {

  private Text data;

  public SampleTaggedMapOutput() {
    this.data = new Text("");
  }

  public SampleTaggedMapOutput(Text data) {
    this.data = data;
  }

  public Writable getData() {
    return data;
  }

  public void write(DataOutput out) throws IOException {
    this.tag.write(out);
    this.data.write(out);
  }

  public void readFields(DataInput in) throws IOException {
    this.tag.readFields(in);
    this.data.readFields(in);
  }
}
