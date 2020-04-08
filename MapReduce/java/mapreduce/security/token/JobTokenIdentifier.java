package org.apache.hadoop.mapreduce.security.token;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.token.TokenIdentifier;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * The token identifier for job token
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class JobTokenIdentifier extends TokenIdentifier {
  private Text jobid;
  final static Text KIND_NAME = new Text("mapreduce.job");
  
  /**
   * Default constructor
   */
  public JobTokenIdentifier() {
    this.jobid = new Text();
  }

  /**
   * Create a job token identifier from a jobid
   * @param jobid the jobid to use
   */
  public JobTokenIdentifier(Text jobid) {
    this.jobid = jobid;
  }

  /** {@inheritDoc} */
  @Override
  public Text getKind() {
    return KIND_NAME;
  }
  
  /** {@inheritDoc} */
  @Override
  public UserGroupInformation getUser() {
    if (jobid == null || "".equals(jobid.toString())) {
      return null;
    }
    return UserGroupInformation.createRemoteUser(jobid.toString());
  }
  
  /**
   * Get the jobid
   * @return the jobid
   */
  public Text getJobId() {
    return jobid;
  }

  /** {@inheritDoc} */
  @Override
  public void readFields(DataInput in) throws IOException {
    jobid.readFields(in);
  }

  /** {@inheritDoc} */
  @Override
  public void write(DataOutput out) throws IOException {
    jobid.write(out);
  }
}
