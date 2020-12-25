package org.apache.hadoop.eclipse.server;

/**
 * Interface for updating/adding jobs to the MapReduce Server view.
 */
public interface IJobListener {

  void jobChanged(HadoopJob job);

  void jobAdded(HadoopJob job);

  void jobRemoved(HadoopJob job);

  void publishStart(JarModule jar);

  void publishDone(JarModule jar);

}
