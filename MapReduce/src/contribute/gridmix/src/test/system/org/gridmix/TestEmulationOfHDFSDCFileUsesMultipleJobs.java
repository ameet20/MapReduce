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
 * Verify the Gridmix emulation of HDFS distributed cache file which uses 
 * different jobs that are submitted with different users.
 */
public class TestEmulationOfHDFSDCFileUsesMultipleJobs extends 
    GridmixSystemTestCase {
  private static final Log LOG = 
      LogFactory.getLog("TestEmulationOfHDFSDCFileUsesMultipleJobs.class");

  /**
   * Generate the input data and HDFS distributed cache file based 
   * on given input trace. Verify the Gridmix emulation of HDFS
   * distributed cache file in RoundRobinResolver mode with 
   * STRESS submission policy.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGenerateAndEmulationOfHDFSDCFile() 
     throws Exception { 
    final long inputSizeInMB = 1024 * 6;
    final String tracePath = getTraceFile("distcache_case9_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
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
   * Verify the Gridmix emulation of HDFS distributed cache
   * file in SubmitterUserResolver mode with STRESS submission policy 
   * by using the existing input data and HDFS distributed cache file. 
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGridmixEmulationOfHDFSPublicDCFile() 
      throws Exception {
    final String tracePath = getTraceFile("distcache_case9_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
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
