package org.apache.hadoop.mapreduce.security.token.delegation;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenSelector;

/**
 * A delegation token that is specialized for MapReduce
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class DelegationTokenSelector
    extends AbstractDelegationTokenSelector<DelegationTokenIdentifier>{

  public DelegationTokenSelector() {
    super(DelegationTokenIdentifier.MAPREDUCE_DELEGATION_KIND);
  }
}
