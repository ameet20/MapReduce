package org.apache.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.mapreduce.server.jobtracker.JTConfig;
import org.apache.hadoop.security.KerberosInfo;

/**
 * Protocol for admin operations. This is a framework-public interface and is
 * NOT_TO_BE_USED_BY_USERS_DIRECTLY.
 */
@KerberosInfo(
    serverPrincipal = JTConfig.JT_USER_NAME)
@InterfaceAudience.Private
@InterfaceStability.Stable
public interface AdminOperationsProtocol extends VersionedProtocol {
  
  /**
   * Version 1: Initial version. Added refreshQueueAcls.
   * Version 2: Added node refresh facility
   * Version 3: Changed refreshQueueAcls to refreshQueues
   */
  public static final long versionID = 3L;

  /**
   * Refresh the queues used by the jobtracker and scheduler.
   * 
   * Access control lists and queue states are refreshed.
   */
  void refreshQueues() throws IOException;
  
  /**
   * Refresh the node list at the {@link JobTracker} 
   */
  void refreshNodes() throws IOException;
}
