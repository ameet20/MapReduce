package org.apache.hadoop.contrib.utils.join;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.mapred.JobConf;

/**
 * This abstract class serves as the base class for the values that 
 * flow from the mappers to the reducers in a data join job. 
 * Typically, in such a job, the mappers will compute the source
 * tag of an input record based on its attributes or based on the 
 * file name of the input file. This tag will be used by the reducers
 * to re-group the values of a given key according to their source tags.
 * 
 */
public abstract class TaggedMapOutput implements Writable {
  protected Text tag;

  public TaggedMapOutput() {
    this.tag = new Text("");
  }

  public Text getTag() {
    return tag;
  }

  public void setTag(Text tag) {
    this.tag = tag;
  }

  public abstract Writable getData();
  
  public TaggedMapOutput clone(JobConf job) {
    return (TaggedMapOutput) WritableUtils.clone(this, job);
  }

}
