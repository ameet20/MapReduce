package org.apache.hadoop.mapred.gridmix;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Echos the UGI offered.
 */
public class EchoUserResolver implements UserResolver {
  public static final Log LOG = LogFactory.getLog(Gridmix.class);

  public EchoUserResolver() {
    LOG.info(" Current user resolver is EchoUserResolver ");
  }

  public synchronized boolean setTargetUsers(URI userdesc, Configuration conf)
  throws IOException {
    return false;
  }

  public synchronized UserGroupInformation getTargetUgi(
    UserGroupInformation ugi) {
    return ugi;
  }

  /**
   * {@inheritDoc}
   * <br><br>
   * Since {@link EchoUserResolver} simply returns the user's name passed as
   * the argument, it doesn't need a target list of users.
   */
  public boolean needsTargetUsersList() {
    return false;
  }
}
