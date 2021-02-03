package org.apache.hadoop.mapred.gridmix;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixConfig;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixRunMode;
import org.apache.hadoop.mapred.gridmix.test.system.UtilsForGridmix;
import org.junit.Assert;
import org.junit.Test;
/**
 * Verify the gridmix jobs compression ratio's of reduce output and 
 * with default and custom ratios.
 */
public class TestCompressionEmulationForUncompressInAndCompressOut
   extends GridmixSystemTestCase { 
   private static final Log LOG = 
       LogFactory.getLog(
           "TestCompressionEmulationForUncompressInAndCompressOut.class");
   final long inputSizeInMB = 1024 * 6;

  /**
   * Generate a uncompressed input data and verify the compression ratios 
   * of reduce output against default output compression ratio.
   * @throws Exception -if an error occurs.
   */
  @Test
  public void testCompressionEmulationOfCompressedOuputWithDefaultRatios() 
      throws Exception { 
    final String tracePath = getTraceFile("compression_case3_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
    final String [] runtimeValues = 
                     {"LOADJOB",
                      RoundRobinUserResolver.class.getName(),
                      "REPLAY",
                      inputSizeInMB + "m",
                      "file://" + UtilsForGridmix.getProxyUsersFile(conf),
                      tracePath};

    final String [] otherArgs = {
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=false",
        "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=true"
    };

    runGridmixAndVerify(runtimeValues, otherArgs, tracePath,
        GridMixRunMode.DATA_GENERATION_AND_RUN_GRIDMIX.getValue());
  }
  
  /**
   * Use existing uncompressed input data and verify the compression ratio 
   * of reduce output against custom output compression ratio and also verify 
   * the compression output file output format.
   * @throws Exception -if an error occurs.
   */
  @Test
  public void testCompressionEmulationOfCompressedOutputWithCustomRatios() 
      throws Exception {
    final String tracePath = getTraceFile("compression_case3_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
    UtilsForGridmix.cleanup(gridmixDir, rtClient.getDaemonConf());
    final String [] runtimeValues = { "LOADJOB",
                                      SubmitterUserResolver.class.getName(),
                                      "STRESS",
                                      inputSizeInMB + "m",
                                      tracePath };

    final String [] otherArgs = { 
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=false",
        "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=true",
        "-D", GridMixConfig.GRIDMIX_OUTPUT_COMPRESSION_RATIO + "=0.38"
    };

    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
        GridMixRunMode.DATA_GENERATION_AND_RUN_GRIDMIX.getValue());
  }
}
