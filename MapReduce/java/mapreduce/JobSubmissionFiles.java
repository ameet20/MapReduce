package org.apache.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.conf.Configuration;
/**
 * A utility to manage job submission files.
 */
@InterfaceAudience.Private
public class JobSubmissionFiles {

  // job submission directory is private!
  final public static FsPermission JOB_DIR_PERMISSION =
    FsPermission.createImmutable((short) 0700); // rwx--------
  //job files are world-wide readable and owner writable
  final public static FsPermission JOB_FILE_PERMISSION = 
    FsPermission.createImmutable((short) 0644); // rw-r--r--
  
  public static Path getJobSplitFile(Path jobSubmissionDir) {
    return new Path(jobSubmissionDir, "job.split");
  }

  public static Path getJobSplitMetaFile(Path jobSubmissionDir) {
    return new Path(jobSubmissionDir, "job.splitmetainfo");
  }
  
  /**
   * Get the job conf path.
   */
  public static Path getJobConfPath(Path jobSubmitDir) {
    return new Path(jobSubmitDir, "job.xml");
  }
    
  /**
   * Get the job jar path.
   */
  public static Path getJobJar(Path jobSubmitDir) {
    return new Path(jobSubmitDir, "job.jar");
  }
  
  /**
   * Get the job distributed cache files path.
   * @param jobSubmitDir
   */
  public static Path getJobDistCacheFiles(Path jobSubmitDir) {
    return new Path(jobSubmitDir, "files");
  }
  /**
   * Get the job distributed cache archives path.
   * @param jobSubmitDir 
   */
  public static Path getJobDistCacheArchives(Path jobSubmitDir) {
    return new Path(jobSubmitDir, "archives");
  }
  /**
   * Get the job distributed cache libjars path.
   * @param jobSubmitDir 
   */
  public static Path getJobDistCacheLibjars(Path jobSubmitDir) {
    return new Path(jobSubmitDir, "libjars");
  }

  /**
   * Initializes the staging directory and returns the path. It also
   * keeps track of all necessary ownership & permissions
   * @param cluster
   * @param conf
   */
  public static Path getStagingDir(Cluster cluster, Configuration conf) 
  throws IOException,InterruptedException {
    Path stagingArea = cluster.getStagingAreaDir();
    FileSystem fs = stagingArea.getFileSystem(conf);
    String realUser;
    String currentUser;
    UserGroupInformation ugi = UserGroupInformation.getLoginUser();
    realUser = ugi.getShortUserName();
    currentUser = UserGroupInformation.getCurrentUser().getShortUserName();
    if (fs.exists(stagingArea)) {
      FileStatus fsStatus = fs.getFileStatus(stagingArea);
      String owner = fsStatus.getOwner();
      if (!(owner.equals(currentUser) || owner.equals(realUser)) || 
          !fsStatus.getPermission().equals(JOB_DIR_PERMISSION)) {
         throw new IOException("The ownership/permissions on the staging " +
                      "directory " + stagingArea + " is not as expected. " + 
                      "It is owned by " + owner + " and permissions are "+ 
                      fsStatus.getPermission() + ". The directory must " +
                      "be owned by the submitter " + currentUser + " or " +
                      "by " + realUser + " and permissions must be rwx------");
      }
    } else {
      fs.mkdirs(stagingArea, 
          new FsPermission(JOB_DIR_PERMISSION));
    }
    return stagingArea;
  }
  
}
