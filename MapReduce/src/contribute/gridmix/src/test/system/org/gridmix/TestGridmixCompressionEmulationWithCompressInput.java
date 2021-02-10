package org.apache.hadoop.mapred.gridmix;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixConfig;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixRunMode;
import org.apache.hadoop.mapred.gridmix.test.system.UtilsForGridmix;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verify the gridmix jobs compression ratios of map input, 
 * map output and reduce output with default and user specified 
 * compression ratios.
 *
 */
public class TestGridmixCompressionEmulationWithCompressInput 
    extends GridmixSystemTestCase {
  private static final Log LOG = 
      LogFactory.getLog(
              "TestGridmixCompressionEmulationWithCompressInput.class");
  final long inputSizeInMB = 1024 * 6;

  /**
   * Generate compressed input data and verify the map input, 
   * map output and reduce output compression ratios of gridmix jobs 
   * against the default compression ratios. 
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGridmixCompressionRatiosAgainstDefaultCompressionRatio() 
      throws Exception { 
    final String tracePath = getTraceFile("compression_case1_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);

    final String [] runtimeValues = 
                     {"LOADJOB",
                      RoundRobinUserResolver.class.getName(),
                      "STRESS",
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
   * Verify map input, map output and  reduce output compression ratios of
   * gridmix jobs against user specified compression ratios. 
   * @throws Exception - if an error occurs.
   */
  @Test
  public void testGridmixOuputCompressionRatiosAgainstCustomRatios() 
      throws Exception { 
    final String tracePath = getTraceFile("compression_case1_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
    UtilsForGridmix.cleanup(gridmixDir, rtClient.getDaemonConf());

    final String [] runtimeValues = 
                     {"LOADJOB",
                      RoundRobinUserResolver.class.getName(),
                      "STRESS",
                      inputSizeInMB + "m",
                      "file://" + UtilsForGridmix.getProxyUsersFile(conf),
                      tracePath};

    final String [] otherArgs = {
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=false",
        "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=true",
        "-D", GridMixConfig.GRIDMIX_INPUT_DECOMPRESS_ENABLE + "=true",
        "-D", GridMixConfig.GRIDMIX_INPUT_COMPRESS_RATIO + "=0.68",
        "-D", GridMixConfig.GRIDMIX_INTERMEDIATE_COMPRESSION_RATIO + "=0.35",
        "-D", GridMixConfig.GRIDMIX_OUTPUT_COMPRESSION_RATIO + "=0.40"
    };
    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
        GridMixRunMode.DATA_GENERATION_AND_RUN_GRIDMIX.getValue());
  }
}
