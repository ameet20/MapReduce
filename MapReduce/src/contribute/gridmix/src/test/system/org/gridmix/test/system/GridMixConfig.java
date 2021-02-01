package org.apache.hadoop.mapred.gridmix.test.system;

import org.apache.hadoop.mapred.gridmix.Gridmix;
import org.apache.hadoop.mapred.gridmix.JobCreator;
import org.apache.hadoop.mapred.gridmix.SleepJob;
import org.apache.hadoop.mapreduce.MRJobConfig;

public class GridMixConfig {

  /**
   *  Gridmix original job id.
   */
  public static final String GRIDMIX_ORIGINAL_JOB_ID = Gridmix.ORIGINAL_JOB_ID;

  /**
   *  Gridmix output directory.
   */
  public static final String GRIDMIX_OUTPUT_DIR = Gridmix.GRIDMIX_OUT_DIR; 

  /**
   * Gridmix job type (LOADJOB/SLEEPJOB).
   */
  public static final String GRIDMIX_JOB_TYPE = JobCreator.GRIDMIX_JOB_TYPE;

  /**
   *  Gridmix submission use queue.
   */
  /* In Gridmix package the visibility of below mentioned 
  properties are protected and it have not visible outside 
  the package. However,it should required for system tests, 
  so it's re-defining in system tests config file.*/
  public static final String GRIDMIX_JOB_SUBMISSION_QUEUE_IN_TRACE = 
      "gridmix.job-submission.use-queue-in-trace";
  
  /**
   *  Gridmix user resolver(RoundRobinUserResolver/
   *  SubmitterUserResolver/EchoUserResolver).
   */
  public static final String GRIDMIX_USER_RESOLVER = Gridmix.GRIDMIX_USR_RSV;

  /**
   *  Gridmix queue depth.
   */
  public static final String GRIDMIX_QUEUE_DEPTH = Gridmix.GRIDMIX_QUE_DEP;

  /* In Gridmix package the visibility of below mentioned 
  property is protected and it should not available for 
  outside the package. However,it should required for 
  system tests, so it's re-defining in system tests config file.*/
  /**
   * Gridmix generate bytes per file.
   */
  public static final String GRIDMIX_BYTES_PER_FILE = 
      "gridmix.gen.bytes.per.file";
  
  /**
   *  Gridmix job submission policy(STRESS/REPLAY/SERIAL).
   */

  public static final String GRIDMIX_SUBMISSION_POLICY =
      "gridmix.job-submission.policy";

  /**
   *  Gridmix minimum file size.
   */
  public static final String GRIDMIX_MINIMUM_FILE_SIZE =
      "gridmix.min.file.size";

  /**
   * Gridmix key fraction.
   */
  public static final String GRIDMIX_KEY_FRC = 
      "gridmix.key.fraction";

  /**
   * Gridmix compression enable
   */
  public static final String GRIDMIX_COMPRESSION_ENABLE =
      "gridmix.compression-emulation.enable";
  /**
   * Gridmix distcache enable
   */
  public static final String GRIDMIX_DISTCACHE_ENABLE = 
      "gridmix.distributed-cache-emulation.enable";

  /**
   * Gridmix input decompression enable.
   */
  public static final String GRIDMIX_INPUT_DECOMPRESS_ENABLE = 
    "gridmix.compression-emulation.input-decompression.enable";

  /**
   * Gridmix input compression ratio.
   */
  public static final String GRIDMIX_INPUT_COMPRESS_RATIO = 
    "gridmix.compression-emulation.map-input.decompression-ratio";

  /**
   * Gridmix intermediate compression ratio.
   */
  public static final String GRIDMIX_INTERMEDIATE_COMPRESSION_RATIO = 
    "gridmix.compression-emulation.map-output.compression-ratio";

  /**
   * Gridmix output compression ratio.
   */
  public static final String GRIDMIX_OUTPUT_COMPRESSION_RATIO = 
      "gridmix.compression-emulation.reduce-output.compression-ratio";

  /**
   * Gridmix distributed cache visibilities.
   */
  public static final String GRIDMIX_DISTCACHE_VISIBILITIES = 
      MRJobConfig.CACHE_FILE_VISIBILITIES;

  /**
   * Gridmix distributed cache files.
   */
  public static final String GRIDMIX_DISTCACHE_FILES = 
      MRJobConfig.CACHE_FILES;
  
  /**
   * Gridmix distributed cache files size.
   */
  public static final String GRIDMIX_DISTCACHE_FILESSIZE = 
      MRJobConfig.CACHE_FILES_SIZES;

  /**
   * Gridmix distributed cache files time stamp.
   */
  public static final String GRIDMIX_DISTCACHE_TIMESTAMP =
      MRJobConfig.CACHE_FILE_TIMESTAMPS;

  /**
   *  Gridmix logger mode.
   */
  public static final String GRIDMIX_LOG_MODE =
      "log4j.logger.org.apache.hadoop.mapred.gridmix";

  /**
   * Gridmix sleep job map task only.
   */
  public static final String GRIDMIX_SLEEPJOB_MAPTASK_ONLY = 
      SleepJob.SLEEPJOB_MAPTASK_ONLY;

  /**
   * Gridmix sleep map maximum time.
   */
  public static final String GRIDMIX_SLEEP_MAP_MAX_TIME = 
      SleepJob.GRIDMIX_SLEEP_MAX_MAP_TIME;

  /**
   * Gridmix sleep reduce maximum time.
   */
  public static final String GRIDMIX_SLEEP_REDUCE_MAX_TIME = 
      SleepJob.GRIDMIX_SLEEP_MAX_REDUCE_TIME;
}
