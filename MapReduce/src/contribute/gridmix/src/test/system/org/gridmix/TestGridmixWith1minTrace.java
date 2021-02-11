package org.apache.hadoop.mapred.gridmix;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.junit.Test;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixConfig;

/**
 * Run the Gridmix with 1 minute MR jobs trace and 
 * verify each job history against the corresponding job story 
 * in a given trace file.
 */
public class TestGridmixWith1minTrace extends GridmixSystemTestCase{
  private static final Log LOG = 
      LogFactory.getLog(TestGridmixWith1minTrace.class);

  /**
   * Generate data and run gridmix by load job with STRESS submission policy
   * in a SubmitterUserResolver mode against 1 minute trace file. 
   * Verify each Gridmix job history with a corresponding job story in the 
   * trace after completion of all the jobs execution.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGridmixWith1minTrace() throws Exception {
    final long inputSizeInMB = cSize * 400;
    String [] runtimeValues = {"LOADJOB",
                               SubmitterUserResolver.class.getName(),
                               "STRESS",
                               inputSizeInMB + "m",
                               map.get("1m")};

    String [] otherArgs = {
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=false",
        "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=false"
    };

    String tracePath = map.get("1m");
    runGridmixAndVerify(runtimeValues, otherArgs, tracePath);
  }
}
