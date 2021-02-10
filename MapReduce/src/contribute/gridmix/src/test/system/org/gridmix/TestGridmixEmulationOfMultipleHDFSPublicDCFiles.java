package org.apache.hadoop.mapred.gridmix;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixConfig;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixRunMode;
import org.apache.hadoop.mapred.gridmix.test.system.UtilsForGridmix;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

/**
 * Verify the Gridmix emulation of Multiple HDFS public distributed 
 * cache files.
 */
public class TestGridmixEmulationOfMultipleHDFSPublicDCFiles 
    extends GridmixSystemTestCase { 
  private static final Log LOG = 
      LogFactory.getLog(
          "TestGridmixEmulationOfMultipleHDFSPublicDCFiles.class");

  /**
   * Generate the compressed input data and dist cache files based 
   * on input trace. Verify the Gridmix emulation of
   * multiple HDFS public distributed cache file.
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGenerateAndEmulationOfMultipleHDFSDCFiles() 
      throws Exception  {
    final long inputSizeInMB = 7168;
    final String tracePath = getTraceFile("distcache_case2_trace");
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
   * Verify the Gridmix emulation of Single HDFS public distributed cache file 
   * by using an existing input compressed data and HDFS dist cache file. 
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGridmixEmulationOfMulitpleHDFSPublicDCFile() 
      throws Exception {
    final String tracePath = getTraceFile("distcache_case2_trace");
    Assert.assertNotNull("Trace file was not found.", tracePath);
    final String [] runtimeValues = {"LOADJOB",
                                     SubmitterUserResolver.class.getName(),
                                     "SERIAL",
                                     tracePath};

    final String [] otherArgs = {
      "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=true"
    };
    runGridmixAndVerify(runtimeValues, otherArgs,  tracePath, 
                        GridMixRunMode.RUN_GRIDMIX.getValue());
  }
}
