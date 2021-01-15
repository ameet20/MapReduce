package org.apache.hadoop.mapred.gridmix;

import java.io.IOException;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Resolves all UGIs to the submitting user.
 */
public class SubmitterUserResolver implements UserResolver {
  public static final Log LOG = LogFactory.getLog(SubmitterUserResolver.class);
  
  private UserGroupInformation ugi = null;

  public SubmitterUserResolver() throws IOException {
    LOG.info(" Current user resolver is SubmitterUserResolver ");
    ugi = UserGroupInformation.getLoginUser();
  }

  public synchronized boolean setTargetUsers(URI userdesc, Configuration conf)
      throws IOException {
    return false;
  }

  public synchronized UserGroupInformation getTargetUgi(
      UserGroupInformation ugi) {
    return this.ugi;
  }

  /**
   * {@inheritDoc}
   * <p>
   * Since {@link SubmitterUserResolver} returns the user name who is running
   * gridmix, it doesn't need a target list of users.
   */
  public boolean needsTargetUsersList() {
    return false;
  }
}
