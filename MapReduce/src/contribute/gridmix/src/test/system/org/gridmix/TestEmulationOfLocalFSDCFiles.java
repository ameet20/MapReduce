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
 * Verify the emulation of local FS distributed cache files.
 *
 */
public class TestEmulationOfLocalFSDCFiles extends GridmixSystemTestCase { 
  private static final Log LOG = 
      LogFactory.getLog("TestEmulationOfLocalFSDCFiles.class");

  /**
   * Generate the input data and distributer cache files.Verify the 
   * gridmix emulation of local file system distributed cache files 
   * in RoundRobinUserResolver mode with REPLAY submission policy.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGenerateInputAndEmulateLocalFSDCFile() 
     throws Exception { 
    final long inputSizeInMB = 1024 * 6;
    final String tracePath = getTraceFile("distcache_case7_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
    final String [] runtimeValues = 
                     {"LOADJOB",
                      RoundRobinUserResolver.class.getName(),
                      "REPLAY",
                      inputSizeInMB + "m",
                      "file://" + UtilsForGridmix.getProxyUsersFile(conf),
                      tracePath};

    final String [] otherArgs = {
       "-D", MRJobConfig.JOB_CANCEL_DELEGATION_TOKEN + "=false",
       "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=true",
       "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=false"
    };
    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
        GridMixRunMode.DATA_GENERATION_AND_RUN_GRIDMIX.getValue());
  }
  
  /**
   * Use existing input and local distributed cache files and  verify 
   * the gridmix emulation of local file system distributed cache 
   * files in SubmitterUserResolver mode with STRESS
   * Submission policy.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testEmulationOfLocalFSDCFile() 
     throws Exception  {
    final String tracePath = getTraceFile("distcache_case7_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
    final String [] runtimeValues = {"LOADJOB",
                                     SubmitterUserResolver.class.getName(),
                                     "STRESS",
                                     tracePath};

    final String [] otherArgs = {
       "-D", MRJobConfig.JOB_CANCEL_DELEGATION_TOKEN + "=false",
      "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=true",
      "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=false"
    };
    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
                        GridMixRunMode.RUN_GRIDMIX.getValue());
  }
}
