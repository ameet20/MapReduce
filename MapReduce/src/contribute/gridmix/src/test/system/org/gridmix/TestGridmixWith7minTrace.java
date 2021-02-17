package org.apache.hadoop.mapred.gridmix;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixConfig;
import org.junit.Test;

/**
 * Run the Gridmix with 7 minutes MR jobs trace and 
 * verify each job history against the corresponding job story 
 * in a given trace file.
 */
public class TestGridmixWith7minTrace extends GridmixSystemTestCase {
  private static final Log LOG = 
      LogFactory.getLog(TestGridmixWith7minTrace.class);

  /**
   * Generate data and run gridmix by sleep job with STRESS submission 
   * policy in a SubmitterUserResolver mode against 7 minute trace file.
   * Verify each Gridmix job history with a corresponding job story 
   * in a trace file after completion of all the jobs execution.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGridmixWith7minTrace() throws Exception {
    final long inputSizeInMB = cSize * 400;
    final long minFileSize = 200 * 1024 * 1024;
    String [] runtimeValues ={"SLEEPJOB",
                              SubmitterUserResolver.class.getName(),
                              "STRESS",
                              inputSizeInMB + "m",
                              map.get("7m")};

    String [] otherArgs = {
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=false",
        "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=false",
        "-D", GridMixConfig.GRIDMIX_MINIMUM_FILE_SIZE + "=" + minFileSize,
        "-D", GridMixConfig.GRIDMIX_JOB_SUBMISSION_QUEUE_IN_TRACE + "=false"
    };
    String tracePath = map.get("7m");
    runGridmixAndVerify(runtimeValues, otherArgs, tracePath);
  }
}
