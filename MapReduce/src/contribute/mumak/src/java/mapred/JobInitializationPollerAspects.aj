package org.apache.hadoop.mapred;

/**
 * This aspect is used for NOT starting the job initialization threads of the capacity scheduler.
 * We schedule the body of these threads manually from the SimulatorJobTracker according to 
 * simulation time.
 */
 
public aspect JobInitializationPollerAspects {

  pointcut overrideInitializationThreadStarts () : 
    (target (JobInitializationPoller) || 
     target (JobInitializationPoller.JobInitializationThread)) && 
    call (public void start());
  
  void around() : overrideInitializationThreadStarts () {
    // no-op
  }
  
  
}
