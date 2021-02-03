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
 * Verify the emulation of HDFS and Local FS distributed cache files against
 * the given input trace file.
 */
public class TestEmulationOfHDFSAndLocalFSDCFiles extends 
    GridmixSystemTestCase {
  private static final Log LOG = 
      LogFactory.getLog("TestEmulationOfLocalFSDCFiles.class");

  /**
   * Generate the input data and distributed cache files for HDFS and 
   * local FS. Verify the gridmix emulation of HDFS and Local FS 
   * distributed cache files in RoundRobinUserResolver mode with STRESS
   * submission policy.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGenerateDataEmulateHDFSAndLocalFSDCFiles() 
     throws Exception  {
    final long inputSizeInMB = 1024 * 6;
    final String tracePath = getTraceFile("distcache_case8_trace");
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
       "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=true",
       "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=false"
    };
    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
        GridMixRunMode.DATA_GENERATION_AND_RUN_GRIDMIX.getValue());
  }
  
  /**
   * Use existing input and distributed cache files for HDFS and
   * local FS. Verify the gridmix emulation of HDFS and Local FS
   * distributed cache files in SubmitterUserResolver mode with REPLAY
   * submission policy.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testEmulationOfHDFSAndLocalFSDCFiles() 
     throws Exception  {
    final String tracePath = getTraceFile("distcache_case8_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
    final String [] runtimeValues ={"LOADJOB",
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
