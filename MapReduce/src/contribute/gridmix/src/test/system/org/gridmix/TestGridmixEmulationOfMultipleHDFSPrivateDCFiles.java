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
 * Verify the Gridmix emulation of Multiple HDFS private distributed 
 * cache files.
 */
public class TestGridmixEmulationOfMultipleHDFSPrivateDCFiles 
    extends GridmixSystemTestCase {
  private static final Log LOG = 
      LogFactory.getLog(
          "TestGridmixEmulationOfMultipleHDFSPrivateDCFiles.class");

  /**
   * Generate input data and multiple HDFS private distributed cache 
   * files based on given input trace.Verify the Gridmix emulation of 
   * multiple private HDFS distributed cache files in RoundRobinUserResolver 
   * mode with SERIAL submission policy.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGenerateAndEmulationOfMultipleHDFSPrivateDCFiles() 
      throws Exception {
    final long inputSize = 6144;
    final String tracePath = getTraceFile("distcache_case4_trace");
    Assert.assertNotNull("Trace file was not found.", tracePath);
    final String [] runtimeValues = 
                     {"LOADJOB",
                      RoundRobinUserResolver.class.getName(),
                      "SERIAL",
                      inputSize+"m",
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
   * Verify the Gridmix emulation of multiple HDFS private distributed 
   * cache files in SubmitterUserResolver mode with STRESS submission 
   * policy by using the existing input data and HDFS private 
   * distributed cache files.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGridmixEmulationOfMultipleHDFSPrivateDCFiles() 
      throws Exception {
    final String tracePath = getTraceFile("distcache_case4_trace");
    Assert.assertNotNull("Trace file was not found.", tracePath);
    final String [] runtimeValues = {"LOADJOB",
                                     SubmitterUserResolver.class.getName(),
                                     "STRESS",
                                     tracePath};
    final String [] otherArgs = {
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=true"
    };
    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
                        GridMixRunMode.RUN_GRIDMIX.getValue());
  }
}
