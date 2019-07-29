package org.apache.hadoop.mapred;

import org.apache.hadoop.mapred.TaskTracker.TaskInProgress;
import org.apache.log4j.Level;

/** Runs a map task. */
class MapTaskRunner extends TaskRunner {
  
  public MapTaskRunner(TaskInProgress task, TaskTracker tracker, JobConf conf) {
    super(task, tracker, conf);
  }
  
  @Override
  public String getChildJavaOpts(JobConf jobConf, String defaultValue) {
    return jobConf.get(JobConf.MAPRED_MAP_TASK_JAVA_OPTS, 
                       super.getChildJavaOpts(jobConf, 
                           JobConf.DEFAULT_MAPRED_TASK_JAVA_OPTS));
  }
  
  @Override
  public int getChildUlimit(JobConf jobConf) {
    return jobConf.getInt(JobConf.MAPRED_MAP_TASK_ULIMIT, 
                          super.getChildUlimit(jobConf));
  }

  @Override
  public String getChildEnv(JobConf jobConf) {
    return jobConf.get(JobConf.MAPRED_MAP_TASK_ENV, super.getChildEnv(jobConf));
  }

  @Override
  public Level getLogLevel(JobConf jobConf) {
    return Level.toLevel(jobConf.get(JobConf.MAPRED_MAP_TASK_LOG_LEVEL, 
                                     JobConf.DEFAULT_LOG_LEVEL.toString()));
  }

}
