package org.apache.hadoop.mapred;

public aspect EagerTaskInitializationListenerAspects {

  pointcut overrideJobAdded (JobInProgressListener listener, JobInProgress job) :
    call (void JobInProgressListener.jobAdded(JobInProgress)) &&
    target (listener) &&
    args (job);
  
  void around(JobInProgressListener listener, JobInProgress job) : 
    overrideJobAdded (listener, job) {
    if (listener instanceof EagerTaskInitializationListener) {
      ((EagerTaskInitializationListener)listener).ttm.initJob(job);
    } else {
      proceed(listener, job);
    }
  }

  pointcut overrideJobRemoved (JobInProgressListener listener, JobInProgress job) :
    call (void JobInProgressListener.jobRemoved(JobInProgress)) &&
    target (listener) &&
    args(job);
  
  void around(JobInProgressListener listener, JobInProgress job) : 
    overrideJobRemoved (listener, job) {
    if (listener instanceof EagerTaskInitializationListener) {
      // no-op
    } else {
      proceed(listener, job);
    }
  }
}
