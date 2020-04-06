package org.apache.hadoop.mapreduce.security.token.delegation;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenIdentifier;

/**
 * A delegation token identifier that is specific to MapReduce.
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class DelegationTokenIdentifier 
    extends AbstractDelegationTokenIdentifier {
  static final Text MAPREDUCE_DELEGATION_KIND = 
    new Text("MAPREDUCE_DELEGATION_TOKEN");

  /**
   * Create an empty delegation token identifier for reading into.
   */
  public DelegationTokenIdentifier() {
  }

  /**
   * Create a new delegation token identifier
   * @param owner the effective username of the token owner
   * @param renewer the username of the renewer
   * @param realUser the real username of the token owner
   */
  public DelegationTokenIdentifier(Text owner, Text renewer, Text realUser) {
    super(owner, renewer, realUser);
  }

  @Override
  public Text getKind() {
    return MAPREDUCE_DELEGATION_KIND;
  }

}
