package org.apache.hadoop.mapred;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.mapreduce.TaskType;

/**
 * A pluggable object for altering the weights of jobs in the fair scheduler,
 * which is used for example by {@link NewJobWeightBooster} to give higher
 * weight to new jobs so that short jobs finish faster.
 * 
 * May implement {@link Configurable} to access configuration parameters.
 */
public interface WeightAdjuster {
  public double adjustWeight(JobInProgress job, TaskType taskType,
      double curWeight);
}
