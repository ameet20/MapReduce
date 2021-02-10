package org.apache.hadoop.mapred.gridmix;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixConfig;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixRunMode;
import org.apache.hadoop.mapred.gridmix.test.system.UtilsForGridmix;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verify the Gridmix emulation of HDFS private distributed cache file.
 */
public class TestGridmixEmulationOfHDFSPrivateDCFile 
    extends GridmixSystemTestCase { 
  private static final Log LOG = 
      LogFactory.getLog("TestGridmixEmulationOfHDFSPrivateDCFile.class");
  /**
   * Generate input data and single HDFS private distributed cache 
   * file based on given input trace.Verify the Gridmix emulation of 
   * single private HDFS distributed cache file in RoundRobinUserResolver 
   * mode with STRESS submission policy.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGenerateAndEmulateOfHDFSPrivateDCFile() 
      throws Exception {
    final long inputSizeInMB = 8192;
    final String tracePath = getTraceFile("distcache_case3_trace");
    Assert.assertNotNull("Trace file was not found.", tracePath);
    final String [] runtimeValues = 
                     {"LOADJOB",
                      RoundRobinUserResolver.class.getName(),
                      "STRESS",
                      inputSizeInMB + "m",
                      "file://" + UtilsForGridmix.getProxyUsersFile(conf),
                      tracePath};

    final String [] otherArgs = {
        "-D", MRJobConfig.JOB_CANCEL_DELEGATION_TOKEN + "=false",
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=true"
    };
    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
        GridMixRunMode.DATA_GENERATION_AND_RUN_GRIDMIX.getValue());
  }
  /**
   * Verify the Gridmix emulation of single HDFS private distributed 
   * cache file in SubmitterUserResolver mode with REPLAY submission 
   * policy by using the existing input data and HDFS private 
   * distributed cache file.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGridmixEmulationOfHDFSPrivateDCFile() 
      throws Exception {
    final String tracePath = getTraceFile("distcache_case3_trace");
    Assert.assertNotNull("Trace file was not found.", tracePath);
    final String [] runtimeValues ={"LOADJOB",
                                    SubmitterUserResolver.class.getName(),
                                    "REPLAY",
                                    tracePath};
    final String [] otherArgs = {
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=true"
    };
    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
                        GridMixRunMode.RUN_GRIDMIX.getValue());
  }
}
