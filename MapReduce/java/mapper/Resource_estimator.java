package org.apache.hadoop.mapred;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class responsible for modeling the resource consumption of running tasks.
 * 
 * For now, we just do temp space for maps
 * 
 * There is one ResourceEstimator per JobInProgress
 *
 */
class ResourceEstimator {

  //Log with JobInProgress
  private static final Log LOG = LogFactory.getLog(
      "org.apache.hadoop.mapred.ResourceEstimator");

  private long completedMapsInputSize;
  private long completedMapsOutputSize;

  private int completedMapsUpdates;
  final private JobInProgress job;
  final private int threshholdToUse;

  public ResourceEstimator(JobInProgress job) {
    this.job = job;
    threshholdToUse = job.desiredMaps()/ 10;
  }

  protected synchronized void updateWithCompletedTask(TaskStatus ts, 
      TaskInProgress tip) {

    //-1 indicates error, which we don't average in.
    if(tip.isMapTask() &&  ts.getOutputSize() != -1)  {
      completedMapsUpdates++;

      completedMapsInputSize+=(tip.getMapInputSize()+1);
      completedMapsOutputSize+=ts.getOutputSize();

      if(LOG.isDebugEnabled()) {
        LOG.debug("completedMapsUpdates:"+completedMapsUpdates+"  "+
                  "completedMapsInputSize:"+completedMapsInputSize+"  " +
                  "completedMapsOutputSize:"+completedMapsOutputSize);
      }
    }
  }

  /**
   * @return estimated length of this job's total map output
   */
  protected synchronized long getEstimatedTotalMapOutputSize()  {
    if(completedMapsUpdates < threshholdToUse) {
      return 0;
    } else {
      long inputSize = job.getInputLength() + job.desiredMaps(); 
      //add desiredMaps() so that randomwriter case doesn't blow up
      long estimate = Math.round((inputSize * 
          completedMapsOutputSize * 2.0)/completedMapsInputSize);
      if (LOG.isDebugEnabled()) {
        LOG.debug("estimate total map output will be " + estimate);
      }
      return estimate;
    }
  }
  
  /**
   * @return estimated length of this job's average map output
   */
  long getEstimatedMapOutputSize() {
    long estimate = 0L;
    if (job.desiredMaps() > 0) {
      estimate = getEstimatedTotalMapOutputSize()  / job.desiredMaps();
    }
    return estimate;
  }

  /**
   * 
   * @return estimated length of this job's average reduce input
   */
  long getEstimatedReduceInputSize() {
    if(job.desiredReduces() == 0) {//no reduce output, so no size
      return 0;
    } else {
      return getEstimatedTotalMapOutputSize() / job.desiredReduces();
      //estimate that each reduce gets an equal share of total map output
    }
  }
  

}
