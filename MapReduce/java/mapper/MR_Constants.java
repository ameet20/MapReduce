package org.apache.hadoop.mapred;

/*******************************
 * Some handy constants
 * 
 *******************************/
interface MRConstants {
  //
  // Timeouts, constants
  //
  public static final long COUNTER_UPDATE_INTERVAL = 60 * 1000;

  //
  // Result codes
  //
  public static int SUCCESS = 0;
  public static int FILE_NOT_FOUND = -1;
  
  /**
   * The custom http header used for the map output length.
   */
  public static final String MAP_OUTPUT_LENGTH = "Map-Output-Length";

  /**
   * The custom http header used for the "raw" map output length.
   */
  public static final String RAW_MAP_OUTPUT_LENGTH = "Raw-Map-Output-Length";

  /**
   * The map task from which the map output data is being transferred
   */
  public static final String FROM_MAP_TASK = "from-map-task";
  
  /**
   * The reduce task number for which this map output is being transferred
   */
  public static final String FOR_REDUCE_TASK = "for-reduce-task";
  
  public static final String WORKDIR = "work";
}
