package org.apache.hadoop.mapred.gridmix;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixConfig;
import org.apache.hadoop.mapred.gridmix.test.system.GridMixRunMode;
import org.apache.hadoop.mapred.gridmix.test.system.UtilsForGridmix;
import org.junit.Assert;
import org.junit.Test;
/**
 * Verify the gridmix jobs compression ratio's of input, 
 * intermediate input and with default/custom ratios.Also verify
 * the compressed output file format is enabled or not.
 *
 */
public class TestCompressionEmulationForCompressInAndUncompressOut 
    extends GridmixSystemTestCase { 
  private static final Log LOG = 
      LogFactory.getLog(
          "TestCompressionEmulationForCompressInAndUncompressOut.class");
  final long inputSizeInMB = 1024 * 6;

  /**
   * Generate a compressed input data and verify the compression ratios 
   * of map input and map output against default compression ratios 
   * and also verify the whether the compressed output file output format 
   * is enabled or not.
   * @throws Exception -if an error occurs.
   */
  @Test
  public void testCompressionEmulationOfCompressedInputWithDefaultRatios() 
      throws Exception {
    final String tracePath = getTraceFile("compression_case2_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
    final String [] runtimeValues = {"LOADJOB",
                                     SubmitterUserResolver.class.getName(),
                                     "STRESS",
                                     inputSizeInMB + "m",
                                     tracePath};

    final String [] otherArgs = {
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=false",
        "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=true"
    };

    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
        GridMixRunMode.DATA_GENERATION_AND_RUN_GRIDMIX.getValue());
  }
  
  /**
   * Use existing compressed input data and verify the compression ratios 
   * of input and intermediate input against custom compression ratios 
   * and also verify the compressed output file output format is enabled or not.
   * @throws Exception -if an error occurs.
   */
  @Test
  public void testCompressionEmulationOfCompressedInputWithCustomRatios() 
      throws Exception {
    final String tracePath = getTraceFile("compression_case2_trace");
    Assert.assertNotNull("Trace file has not found.", tracePath);
    UtilsForGridmix.cleanup(gridmixDir, rtClient.getDaemonConf());
    final String [] runtimeValues = {"LOADJOB",
                                     SubmitterUserResolver.class.getName(),
                                     "STRESS",
                                     inputSizeInMB + "m",
                                     tracePath};

    final String [] otherArgs = {
        "-D", GridMixConfig.GRIDMIX_DISTCACHE_ENABLE + "=false",
        "-D", GridMixConfig.GRIDMIX_COMPRESSION_ENABLE + "=true",
        "-D", GridMixConfig.GRIDMIX_INPUT_DECOMPRESS_ENABLE + "=true",
        "-D", GridMixConfig.GRIDMIX_INPUT_COMPRESS_RATIO + "=0.58",
        "-D", GridMixConfig.GRIDMIX_INTERMEDIATE_COMPRESSION_RATIO + "=0.42"
    };

    runGridmixAndVerify(runtimeValues, otherArgs, tracePath, 
        GridMixRunMode.DATA_GENERATION_AND_RUN_GRIDMIX.getValue());
  }
}
