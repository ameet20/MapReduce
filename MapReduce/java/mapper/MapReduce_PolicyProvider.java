package org.apache.hadoop.mapred;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.protocol.ClientProtocol;
import org.apache.hadoop.security.RefreshUserMappingsProtocol;
import org.apache.hadoop.security.authorize.PolicyProvider;
import org.apache.hadoop.security.authorize.RefreshAuthorizationPolicyProtocol;
import org.apache.hadoop.security.authorize.Service;
import org.apache.hadoop.tools.GetUserMappingsProtocol;

/**
 * {@link PolicyProvider} for Map-Reduce protocols.
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class MapReducePolicyProvider extends PolicyProvider {
  private static final Service[] mapReduceServices = 
    new Service[] {
      new Service("security.inter.tracker.protocol.acl", 
                  InterTrackerProtocol.class),
      new Service("security.job.submission.protocol.acl",
                  ClientProtocol.class),
      new Service("security.task.umbilical.protocol.acl", 
                  TaskUmbilicalProtocol.class),
      new Service("security.refresh.policy.protocol.acl", 
                  RefreshAuthorizationPolicyProtocol.class),
      new Service("security.refresh.user.mappings.protocol.acl", 
                  RefreshUserMappingsProtocol.class),
      new Service("security.admin.operations.protocol.acl", 
                  AdminOperationsProtocol.class),
      new Service("security.get.user.mappings.protocol.acl",
                  GetUserMappingsProtocol.class)
  };
  
  @Override
  public Service[] getServices() {
    return mapReduceServices;
  }

}
