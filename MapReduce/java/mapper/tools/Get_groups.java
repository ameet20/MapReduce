package org.apache.hadoop.mapred.tools;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobTracker;
import org.apache.hadoop.tools.GetGroupsBase;
import org.apache.hadoop.util.ToolRunner;

/**
 * MR implementation of a tool for getting the groups which a given user
 * belongs to.
 */
public class GetGroups extends GetGroupsBase {

  static {
    Configuration.addDefaultResource("mapred-default.xml");
    Configuration.addDefaultResource("mapred-site.xml");
  }
  
  GetGroups(Configuration conf) {
    super(conf);
  }
  
  GetGroups(Configuration conf, PrintStream out) {
    super(conf, out);
  }

  @Override
  protected InetSocketAddress getProtocolAddress(Configuration conf)
      throws IOException {
    return JobTracker.getAddress(conf);
  }

  public static void main(String[] argv) throws Exception {
    int res = ToolRunner.run(new GetGroups(new Configuration()), argv);
    System.exit(res);
  }
}
