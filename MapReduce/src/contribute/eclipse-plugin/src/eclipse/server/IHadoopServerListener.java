package org.apache.hadoop.eclipse.servers;

import org.apache.hadoop.eclipse.server.HadoopServer;

/**
 * Interface for monitoring server changes
 */
public interface IHadoopServerListener {
  void serverChanged(HadoopServer location, int type);
}
