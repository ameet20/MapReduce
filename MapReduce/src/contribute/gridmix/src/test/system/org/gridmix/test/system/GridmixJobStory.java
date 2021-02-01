package org.apache.hadoop.mapred.gridmix.test.system;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.tools.rumen.ZombieJobProducer;
import org.apache.hadoop.tools.rumen.ZombieJob;
import org.apache.hadoop.conf.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Build the job stories with a given trace file. 
 */
public class GridmixJobStory {
  private static Log LOG = LogFactory.getLog(GridmixJobStory.class);
  private Path path;
  private Map<JobID, ZombieJob> zombieJobs;
  private Configuration conf;
  
  public GridmixJobStory(Path path, Configuration conf) {
    this.path = path;
    this.conf = conf;
    try {
       zombieJobs = buildJobStories();
       if(zombieJobs == null) {
          throw new NullPointerException("No jobs found in a " 
              + " given trace file.");
       }
    } catch (IOException ioe) {
      LOG.warn("Error:" + ioe.getMessage());
    } catch (NullPointerException npe) {
      LOG.warn("Error:" + npe.getMessage());
    }
  }
  
  /**
   * Get the zombie jobs as a map.
   * @return the zombie jobs map.
   */
  public Map<JobID, ZombieJob> getZombieJobs() {
    return zombieJobs;
  }
  
  /**
   * Get the zombie job of a given job id.
   * @param jobId - gridmix job id.
   * @return - the zombie job object.
   */
  public ZombieJob getZombieJob(JobID jobId) {
    return zombieJobs.get(jobId);
  }
  
  private Map<JobID, ZombieJob> buildJobStories() throws IOException {
    ZombieJobProducer zjp = new ZombieJobProducer(path,null, conf);
    Map<JobID, ZombieJob> hm = new HashMap<JobID, ZombieJob>();
    ZombieJob zj = zjp.getNextJob();
    while (zj != null) {
      hm.put(zj.getJobID(),zj);
      zj = zjp.getNextJob();
    }
    if (hm.size() == 0) {
      return null;
    } else {
      return hm;
    }
  }
}
