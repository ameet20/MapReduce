package org.apache.hadoop.mapred.gridmix.test.system;
/**
 * Gridmix run modes. 
 *
 */
public enum GridMixRunMode {
   DATA_GENERATION(1), RUN_GRIDMIX(2), DATA_GENERATION_AND_RUN_GRIDMIX(3);
   private int mode;

   GridMixRunMode (int mode) {
      this.mode = mode;
   }
   
   public int getValue() {
     return mode;
   }
}
