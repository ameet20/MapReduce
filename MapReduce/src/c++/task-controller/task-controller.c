#include "task-controller.h"

//struct to store the user details
struct passwd *user_detail = NULL;

//LOGFILE
FILE *LOGFILE;

//placeholder for global cleanup operations
void cleanup() {
  free_configurations();
}

//change the user to passed user for executing/killing tasks
int change_user(const char * user) {
  if (get_user_details(user) < 0) {
    return -1;
  }

  if(initgroups(user_detail->pw_name, user_detail->pw_gid) != 0) {
    fprintf(LOGFILE, "unable to initgroups : %s\n", strerror(errno));
	  cleanup();
	  return SETUID_OPER_FAILED;
  }

  errno = 0;

  setgid(user_detail->pw_gid);
  if (errno != 0) {
    fprintf(LOGFILE, "unable to setgid : %s\n", strerror(errno));
    cleanup();
    return SETUID_OPER_FAILED;
  }

  setegid(user_detail->pw_gid);
  if (errno != 0) {
    fprintf(LOGFILE, "unable to setegid : %s\n", strerror(errno));
    cleanup();
    return SETUID_OPER_FAILED;
  }

  setuid(user_detail->pw_uid);
  if (errno != 0) {
    fprintf(LOGFILE, "unable to setuid : %s\n", strerror(errno));
    cleanup();
    return SETUID_OPER_FAILED;
  }

  seteuid(user_detail->pw_uid);
  if (errno != 0) {
    fprintf(LOGFILE, "unable to seteuid : %s\n", strerror(errno));
    cleanup();
    return SETUID_OPER_FAILED;
  }
  return 0;
}

/**
 * Checks the passed value for the variable config_key against the values in
 * the configuration.
 * Returns 0 if the passed value is found in the configuration,
 *        -1 otherwise
 */
int check_variable_against_config(const char *config_key,
    const char *passed_value) {

  if (config_key == NULL || passed_value == NULL) {
    return -1;
  }

  int found = -1;

  const char **config_value = get_values(config_key);

  if (config_value == NULL) {
    fprintf(LOGFILE, "%s is not configured.\n", config_key);
    return -1;
  }

  char *full_config_value = (char *)get_value(config_key);

  char **config_val_ptr = (char **) config_value;
  while (*config_val_ptr != NULL) {
    if (strcmp(*config_val_ptr, passed_value) == 0) {
      found = 0;
      break;
    }
    config_val_ptr++;
  }

  if (found != 0) {
    fprintf(
        LOGFILE,
        "Invalid value passed: \
        Configured value of %s is %s. \
        Passed value is %s.\n",
        config_key, full_config_value, passed_value);
  }
  free(full_config_value);
  free(config_value);
  return found;
}

/**
 * Utility function to concatenate argB to argA using the concat_pattern
 */
char *concatenate(char *concat_pattern, char *return_path_name, int numArgs,
    ...) {
  va_list ap;
  va_start(ap, numArgs);
  int strlen_args = 0;
  char *arg = NULL;
  int j;
  for (j = 0; j < numArgs; j++) {
    arg = va_arg(ap, char*);
    if (arg == NULL) {
      fprintf(LOGFILE, "One of the arguments passed for %s in null.\n",
          return_path_name);
      return NULL;
    }
    strlen_args += strlen(arg);
  }
  va_end(ap);

  char *return_path = NULL;
  int str_len = strlen(concat_pattern) + strlen_args;

  return_path = (char *) malloc(sizeof(char) * (str_len + 1));
  if (return_path == NULL) {
    fprintf(LOGFILE, "Unable to allocate memory for %s.\n", return_path_name);
    return NULL;
  }
  memset(return_path, '\0', str_len + 1);
  va_start(ap, numArgs);
  vsnprintf(return_path, str_len, concat_pattern, ap);
  va_end(ap);
  return return_path;
}

/**
 * Get the job-directory path from tt_root, user name and job-id
 */
char *get_job_directory(const char * tt_root, const char *user,
    const char *jobid) {
  return concatenate(TT_JOB_DIR_PATTERN, "job_dir_path", 3, tt_root, user,
      jobid);
}

/**
 * Get the user directory of a particular user
 */
char *get_user_directory(const char *tt_root, const char *user) {
  return concatenate(USER_DIR_PATTERN, "user_dir_path", 2, tt_root, user);
}

/**
 * Get the distributed cache directory for a particular user
 */
char *get_distributed_cache_directory(const char *tt_root, const char *user,
    const char* unique_string) {
  return concatenate(USER_DISTRIBUTED_CACHE_DIR_PATTERN, 
      "dist_cache_unique_path", 3, tt_root, user, unique_string);
}

char *get_job_work_directory(const char *job_dir) {
  return concatenate(JOB_DIR_TO_JOB_WORK_PATTERN, "job_work_dir_path", 2,
      job_dir, "");
}
/**
 * Get the attempt directory for the given attempt_id
 */
char *get_attempt_directory(const char *job_dir, const char *attempt_id) {
  return concatenate(JOB_DIR_TO_ATTEMPT_DIR_PATTERN, "attempt_dir_path", 2,
      job_dir, attempt_id);
}

/*
 * Get the path to the task launcher file which is created by the TT
 */
char *get_task_launcher_file(const char *job_dir, const char *attempt_dir) {
  return concatenate(TASK_SCRIPT_PATTERN, "task_script_path", 2, job_dir,
      attempt_dir);
}

/*
 * Builds the full path of the dir(localTaskDir or localWorkDir)
 * tt_root : is the base path(i.e. mapred-local-dir) sent to task-controller
 * dir_to_be_deleted : is either taskDir($taskId) OR taskWorkDir($taskId/work)
 */
char *get_task_dir_path(const char *tt_root, const char *user,
                        const char *jobid, const char *dir_to_be_deleted) {
  return concatenate(TT_LOCAL_TASK_DIR_PATTERN, "task_dir_full_path", 4,
                     tt_root, user, jobid, dir_to_be_deleted);
}

/**
 * Get the log directory for the given attempt.
 */
char *get_task_log_dir(const char *log_dir, const char *job_id, 
    const char *attempt_id) {
  return concatenate(ATTEMPT_LOG_DIR_PATTERN, "task_log_dir", 3, log_dir,
      job_id, attempt_id);
}

/**
 * Get the log directory for the given job.
 */
char *get_job_log_dir(const char *log_dir, const char *job_id) {
  return concatenate(JOB_LOG_DIR_PATTERN, "job_log_dir", 2, log_dir, job_id);
}

/**
 * Get the job ACLs file for the given job log dir.
 */
char *get_job_acls_file(const char *log_dir) {
  return concatenate(JOB_LOG_DIR_TO_JOB_ACLS_FILE_PATTERN, "job_acls_file",
                     1, log_dir);
}

/**
 * Function to check if the passed tt_root is present in mapreduce.cluster.local.dir
 * the task-controller is configured with.
 */
int check_tt_root(const char *tt_root) {
  return check_variable_against_config(TT_SYS_DIR_KEY, tt_root);
}

/**
 * Function to check if the constructed path and absolute path of the task
 * launcher file resolve to one and same. This is done so as to avoid
 * security pitfalls because of relative path components in the file name.
 */
int check_path_for_relative_components(char *path) {
  char * resolved_path = (char *) canonicalize_file_name(path);
  if (resolved_path == NULL) {
    fprintf(LOGFILE,
        "Error resolving the path: %s. Passed path: %s\n",
        strerror(errno), path);
    return ERROR_RESOLVING_FILE_PATH;
  }
  if (strcmp(resolved_path, path) != 0) {
    fprintf(LOGFILE,
        "Relative path components in the path: %s. Resolved path: %s\n",
        path, resolved_path);
    free(resolved_path);
    return RELATIVE_PATH_COMPONENTS_IN_FILE_PATH;
  }
  free(resolved_path);
  return 0;
}

/**
 * Function to change the owner/group of a given path.
 */
static int change_owner(const char *path, uid_t uid, gid_t gid) {
  int exit_code = chown(path, uid, gid);
  if (exit_code != 0) {
    fprintf(LOGFILE, "chown %d:%d for path %s failed: %s.\n", uid, gid, path,
        strerror(errno));
  }
  return exit_code;
}

/**
 * Function to change the mode of a given path.
 */
static int change_mode(const char *path, mode_t mode) {
  int exit_code = chmod(path, mode);
  if (exit_code != 0) {
    fprintf(LOGFILE, "chmod %d of path %s failed: %s.\n", mode, path,
        strerror(errno));
  }
  return exit_code;
}

/**
 * Function to change permissions of the given path. It does the following
 * recursively:
 *    1) changes the owner/group of the paths to the passed owner/group
 *    2) changes the file permission to the passed file_mode and directory
 *       permission to the passed dir_mode
 *
 * should_check_ownership : boolean to enable checking of ownership of each path
 */
static int secure_path(const char *path, uid_t uid, gid_t gid,
    mode_t file_mode, mode_t dir_mode, int should_check_ownership) {
  FTS *tree = NULL; // the file hierarchy
  FTSENT *entry = NULL; // a file in the hierarchy
  char *paths[] = { (char *) path, NULL };//array needs to be NULL-terminated
  int process_path = 0;
  int dir = 0;
  int error_code = 0;
  int done = 0;

  // Get physical locations and don't resolve the symlinks.
  // Don't change directory while walking the directory.
  int ftsoptions = FTS_PHYSICAL | FTS_NOCHDIR;

  tree = fts_open(paths, ftsoptions, NULL);
  if (tree == NULL) {
    fprintf(LOGFILE,
        "Cannot open file traversal structure for the path %s:%s.\n", path,
        strerror(errno));
    return -1;
  }

  while (((entry = fts_read(tree)) != NULL) && !done) {
    dir = 0;
    switch (entry->fts_info) {
    case FTS_D:
      // A directory being visited in pre-order.
      // We change ownership of directories in post-order.
      // so ignore the pre-order visit.
      process_path = 0;
      break;
    case FTS_DC:
      // A directory that causes a cycle in the tree
      // We don't expect cycles, ignore.
      process_path = 0;
      break;
    case FTS_DNR:
      // A directory which cannot be read
      // Ignore and set error code.
      process_path = 0;
      error_code = -1;
      break;
    case FTS_DOT:
      // "."  or ".."
      process_path = 0;
      break;
    case FTS_F:
      // A regular file
      process_path = 1;
      break;
    case FTS_DP:
      // A directory being visited in post-order
      if (entry->fts_level == 0) {
        // root directory. Done with traversing.
        done = 1;
      }
      process_path = 1;
      dir = 1;
      break;
    case FTS_SL:
      // A symbolic link
      // We don't want to change-ownership(and set-permissions) for the file/dir
      // pointed to by any symlink.
      process_path = 0;
      break;
    case FTS_SLNONE:
      // A symbolic link with a nonexistent target
      process_path = 0;
      break;
    case FTS_NS:
      // A  file for which no stat(2) information was available
      // Ignore and set error code
      process_path = 0;
      error_code = -1;
      break;
    case FTS_ERR:
      // An error return. Ignore and set error code.
      process_path = 0;
      error_code = -1;
      break;
    case FTS_DEFAULT:
      // File that doesn't belong to any of the above type. Ignore.
      process_path = 0;
      break;
    default:
      // None of the above. Ignore and set error code
      process_path = 0;
      error_code = -1;
    }

    if (error_code != 0) {
      break;
    }
    if (!process_path) {
      continue;
    }
    error_code = secure_single_path(entry->fts_path, uid, gid,
      (dir ? dir_mode : file_mode), should_check_ownership);

  }
  if (fts_close(tree) != 0) {
    fprintf(LOGFILE, "couldn't close file traversal structure:%s.\n",
        strerror(errno));
  }
  return error_code;
}

/**
 * Function to change ownership and permissions of the given path. 
 * This call sets ownership and permissions just for the path, not recursive.  
 */
int secure_single_path(char *path, uid_t uid, gid_t gid,
    mode_t perm, int should_check_ownership) {
  int error_code = 0;
  if (should_check_ownership && 
      (check_ownership(path, uid, gid) != 0)) {
    fprintf(LOGFILE,
      "Invalid file path. %s not user/group owned by the tasktracker.\n", path);
    error_code = -1;
  } else if (change_owner(path, uid, gid) != 0) {
    fprintf(LOGFILE, "couldn't change the ownership of %s\n", path);
    error_code = -3;
  } else if (change_mode(path, perm) != 0) {
    fprintf(LOGFILE, "couldn't change the permissions of %s\n", path);
    error_code = -3;
  }
  return error_code;
}

/**
 * Function to prepare the attempt directories for the task JVM.
 * This is done by changing the ownership of the attempt directory recursively
 * to the job owner. We do the following:
 *  *  sudo chown user:mapred -R taskTracker/$user/jobcache/$jobid/$attemptid/
 *  *  sudo chmod 2770 -R taskTracker/$user/jobcache/$jobid/$attemptid/
 */
int prepare_attempt_directories(const char *job_id, const char *attempt_id,
    const char *user) {
  if (job_id == NULL || attempt_id == NULL || user == NULL) {
    fprintf(LOGFILE, "Either attempt_id is null or the user passed is null.\n");
    return INVALID_ARGUMENT_NUMBER;
  }

  gid_t tasktracker_gid = getegid(); // the group permissions of the binary.

  if (get_user_details(user) < 0) {
    fprintf(LOGFILE, "Couldn't get the user details of %s.\n", user);
    return INVALID_USER_NAME;
  }

  char **local_dir = (char **) get_values(TT_SYS_DIR_KEY);

  if (local_dir == NULL) {
    fprintf(LOGFILE, "%s is not configured.\n", TT_SYS_DIR_KEY);
    cleanup();
    return PREPARE_ATTEMPT_DIRECTORIES_FAILED;
  }

  char *full_local_dir_str = (char *) get_value(TT_SYS_DIR_KEY);
#ifdef DEBUG
  fprintf(LOGFILE, "Value from config for %s is %s.\n", TT_SYS_DIR_KEY,
      full_local_dir_str);
#endif

  char *job_dir;
  char *attempt_dir;
  char **local_dir_ptr = local_dir;
  int failed = 0;
  while (*local_dir_ptr != NULL) {
    job_dir = get_job_directory(*local_dir_ptr, user, job_id);
    if (job_dir == NULL) {
      fprintf(LOGFILE, "Couldn't get job directory for %s.\n", job_id);
      failed = 1;
      break;
    }

    // prepare attempt-dir in each of the mapreduce.cluster.local.dir
    attempt_dir = get_attempt_directory(job_dir, attempt_id);
    if (attempt_dir == NULL) {
      fprintf(LOGFILE, "Couldn't get attempt directory for %s.\n", attempt_id);
      failed = 1;
      free(job_dir);
      break;
    }

    struct stat filestat;
    if (stat(attempt_dir, &filestat) != 0) {
      if (errno == ENOENT) {
#ifdef DEBUG
        fprintf(LOGFILE,
            "attempt_dir %s doesn't exist. Not doing anything.\n", attempt_dir);
#endif
      } else {
        // stat failed because of something else!
        fprintf(LOGFILE, "Failed to stat the attempt_dir %s\n", attempt_dir);
        failed = 1;
        free(attempt_dir);
        free(job_dir);
        break;
      }
    } else if (secure_path(attempt_dir, user_detail->pw_uid,
               tasktracker_gid, S_IRWXU | S_IRWXG, S_ISGID | S_IRWXU | S_IRWXG,
               1) != 0) {
      // No setgid on files and setgid on dirs, 770
      fprintf(LOGFILE, "Failed to secure the attempt_dir %s\n", attempt_dir);
      failed = 1;
      free(attempt_dir);
      free(job_dir);
      break;
    }

    local_dir_ptr++;
    free(attempt_dir);
    free(job_dir);
  }
  free(local_dir);
  free(full_local_dir_str);

  cleanup();
  if (failed) {
    return PREPARE_ATTEMPT_DIRECTORIES_FAILED;
  }
  return 0;
}

/**
 * Function to prepare the job log dir(and job acls file in it) for the child.
 * It gives the user ownership of the job's log-dir to the user and
 * group ownership to the user running tasktracker(i.e. tt_user).
 *
 *   *  sudo chown user:mapred log-dir/userlogs/$jobid
 *   *    if user is not $tt_user,
 *   *      sudo chmod 2570 log-dir/userlogs/$jobid
 *   *    else
 *   *      sudo chmod 2770 log-dir/userlogs/$jobid
 *   *  sudo chown user:mapred log-dir/userlogs/$jobid/job-acls.xml
 *   *    if user is not $tt_user,
 *   *      sudo chmod 2570 log-dir/userlogs/$jobid/job-acls.xml
 *   *    else
 *   *      sudo chmod 2770 log-dir/userlogs/$jobid/job-acls.xml 
 */
int prepare_job_logs(const char *log_dir, const char *job_id,
    mode_t permissions) {

  char *job_log_dir = get_job_log_dir(log_dir, job_id);
  if (job_log_dir == NULL) {
    fprintf(LOGFILE, "Couldn't get job log directory %s.\n", job_log_dir);
    return -1;
  }

  struct stat filestat;
  if (stat(job_log_dir, &filestat) != 0) {
    if (errno == ENOENT) {
#ifdef DEBUG
      fprintf(LOGFILE, "job_log_dir %s doesn't exist. Not doing anything.\n",
          job_log_dir);
#endif
      free(job_log_dir);
      return 0;
    } else {
      // stat failed because of something else!
      fprintf(LOGFILE, "Failed to stat the job log dir %s\n", job_log_dir);
      free(job_log_dir);
      return -1;
    }
  }

  gid_t tasktracker_gid = getegid(); // the group permissions of the binary.
  // job log directory should not be set permissions recursively
  // because, on tt restart/reinit, it would contain directories of earlier run
  if (secure_single_path(job_log_dir, user_detail->pw_uid, tasktracker_gid,
      S_ISGID | permissions, 1) != 0) {
    fprintf(LOGFILE, "Failed to secure the log_dir %s\n", job_log_dir);
    free(job_log_dir);
    return -1;
  }

  //set ownership and permissions for job_log_dir/job-acls.xml, if exists.
  char *job_acls_file = get_job_acls_file(job_log_dir);
  if (job_acls_file == NULL) {
    fprintf(LOGFILE, "Couldn't get job acls file %s.\n", job_acls_file);
    free(job_log_dir);
    return -1; 
  }

  struct stat filestat1;
  if (stat(job_acls_file, &filestat1) != 0) {
    if (errno == ENOENT) {
#ifdef DEBUG
      fprintf(LOGFILE, "job_acls_file %s doesn't exist. Not doing anything.\n",
          job_acls_file);
#endif
      free(job_acls_file);
      free(job_log_dir);
      return 0;
    } else {
      // stat failed because of something else!
      fprintf(LOGFILE, "Failed to stat the job_acls_file %s\n", job_acls_file);
      free(job_acls_file);
      free(job_log_dir);
      return -1;
    }
  }

  if (secure_single_path(job_acls_file, user_detail->pw_uid, tasktracker_gid,
      permissions, 1) != 0) {
    fprintf(LOGFILE, "Failed to secure the job acls file %s\n", job_acls_file);
    free(job_acls_file);
    free(job_log_dir);
    return -1;
  }
  free(job_acls_file);
  free(job_log_dir);
  return 0;
}

/**
 * Function to prepare the task logs for the child. It gives the user
 * ownership of the attempt's log-dir to the user and group ownership to the
 * user running tasktracker.
 *     *  sudo chown user:mapred log-dir/userlogs/$jobid/$attemptid
 *     *  sudo chmod -R 2770 log-dir/userlogs/$jobid/$attemptid
 */
int prepare_task_logs(const char *log_dir, const char *job_id, 
    const char *task_id) {

  char *task_log_dir = get_task_log_dir(log_dir, job_id, task_id);
  if (task_log_dir == NULL) {
    fprintf(LOGFILE, "Couldn't get task_log directory %s.\n", task_log_dir);
    return -1;
  }

  struct stat filestat;
  if (stat(task_log_dir, &filestat) != 0) {
    if (errno == ENOENT) {
      // See TaskRunner.java to see that an absent log-dir doesn't fail the task.
#ifdef DEBUG
      fprintf(LOGFILE, "task_log_dir %s doesn't exist. Not doing anything.\n",
          task_log_dir);
#endif
      free(task_log_dir);
      return 0;
    } else {
      // stat failed because of something else!
      fprintf(LOGFILE, "Failed to stat the task_log_dir %s\n", task_log_dir);
      free(task_log_dir);
      return -1;
    }
  }

  gid_t tasktracker_gid = getegid(); // the group permissions of the binary.
  if (secure_path(task_log_dir, user_detail->pw_uid, tasktracker_gid,
      S_IRWXU | S_IRWXG, S_ISGID | S_IRWXU | S_IRWXG, 1) != 0) {
    // setgid on dirs but not files, 770. As of now, there are no files though
    fprintf(LOGFILE, "Failed to secure the log_dir %s\n", task_log_dir);
    free(task_log_dir);
    return -1;
  }
  free(task_log_dir);
  return 0;
}

//function used to populate and user_details structure.
int get_user_details(const char *user) {
  if (user_detail == NULL) {
    user_detail = getpwnam(user);
    if (user_detail == NULL) {
      fprintf(LOGFILE, "Invalid user\n");
      return -1;
    }
  }
  return 0;
}

/*
 * Function to check if the TaskTracker actually owns the file.
 * Or it has right ownership already. 
 */
int check_ownership(char *path, uid_t uid, gid_t gid) {
  struct stat filestat;
  if (stat(path, &filestat) != 0) {
    return UNABLE_TO_STAT_FILE;
  }
  // check user/group. User should be TaskTracker user, group can either be
  // TaskTracker's primary group or the special group to which binary's
  // permissions are set.
  // Or it can be the user/group owned by uid and gid passed. 
  if ((getuid() != filestat.st_uid || (getgid() != filestat.st_gid && getegid()
      != filestat.st_gid)) &&
      ((uid != filestat.st_uid) || (gid != filestat.st_gid))) {
    return FILE_NOT_OWNED_BY_TASKTRACKER;
  }
  return 0;
}

/**
 * Function to initialize the user directories of a user.
 * It does the following:
 *     *  sudo chown user:mapred -R taskTracker/$user
 *     *  if user is not $tt_user,
 *     *    sudo chmod 2570 -R taskTracker/$user
 *     *  else // user is tt_user
 *     *    sudo chmod 2770 -R taskTracker/$user
 * This is done once per every user on the TaskTracker.
 */
int initialize_user(const char *user) {

  if (user == NULL) {
    fprintf(LOGFILE, "user passed is null.\n");
    return INVALID_ARGUMENT_NUMBER;
  }

  if (get_user_details(user) < 0) {
    fprintf(LOGFILE, "Couldn't get the user details of %s", user);
    return INVALID_USER_NAME;
  }

  gid_t tasktracker_gid = getegid(); // the group permissions of the binary.

  char **local_dir = (char **) get_values(TT_SYS_DIR_KEY);
  if (local_dir == NULL) {
    fprintf(LOGFILE, "%s is not configured.\n", TT_SYS_DIR_KEY);
    cleanup();
    return INVALID_TT_ROOT;
  }

  char *full_local_dir_str = (char *) get_value(TT_SYS_DIR_KEY);
#ifdef DEBUG
  fprintf(LOGFILE, "Value from config for %s is %s.\n", TT_SYS_DIR_KEY,
      full_local_dir_str);
#endif

  int is_tt_user = (user_detail->pw_uid == getuid());
  
  // for tt_user, set 770 permissions; otherwise set 570
  mode_t permissions = is_tt_user ? (S_IRWXU | S_IRWXG)
                                  : (S_IRUSR | S_IXUSR | S_IRWXG);
  char *user_dir;
  char **local_dir_ptr = local_dir;
  int failed = 0;
  while (*local_dir_ptr != NULL) {
    user_dir = get_user_directory(*local_dir_ptr, user);
    if (user_dir == NULL) {
      fprintf(LOGFILE, "Couldn't get userdir directory for %s.\n", user);
      failed = 1;
      break;
    }

    struct stat filestat;
    if (stat(user_dir, &filestat) != 0) {
      if (errno == ENOENT) {
#ifdef DEBUG
        fprintf(LOGFILE, "user_dir %s doesn't exist. Not doing anything.\n",
            user_dir);
#endif
      } else {
        // stat failed because of something else!
        fprintf(LOGFILE, "Failed to stat the user_dir %s\n",
            user_dir);
        failed = 1;
        free(user_dir);
        break;
      }
    } else if (secure_path(user_dir, user_detail->pw_uid,
        tasktracker_gid, permissions, S_ISGID | permissions, 1) != 0) {
      // No setgid on files and setgid on dirs,
      // 770 for tt_user and 570 for any other user
      fprintf(LOGFILE, "Failed to secure the user_dir %s\n",
              user_dir);
      failed = 1;
      free(user_dir);
      break;
    }

    local_dir_ptr++;
    free(user_dir);
  }
  free(local_dir);
  free(full_local_dir_str);
  cleanup();
  if (failed) {
    return INITIALIZE_USER_FAILED;
  }
  return 0;
}

/**
 * Function to prepare the job directories for the task JVM.
 * We do the following:
 *     *  sudo chown user:mapred -R taskTracker/$user/jobcache/$jobid
 *     *  sudo chown user:mapred -R logs/userlogs/$jobid
 *     *  if user is not $tt_user,
 *     *    sudo chmod 2570 -R taskTracker/$user/jobcache/$jobid
 *     *    sudo chmod 2570 -R logs/userlogs/$jobid
 *     *  else // user is tt_user
 *     *    sudo chmod 2770 -R taskTracker/$user/jobcache/$jobid
 *     *    sudo chmod 2770 -R logs/userlogs/$jobid
 *     *
 *     *  For any user, sudo chmod 2770 taskTracker/$user/jobcache/$jobid/work
 */
int initialize_job(const char *jobid, const char *user) {
  if (jobid == NULL || user == NULL) {
    fprintf(LOGFILE, "Either jobid is null or the user passed is null.\n");
    return INVALID_ARGUMENT_NUMBER;
  }

  if (get_user_details(user) < 0) {
    fprintf(LOGFILE, "Couldn't get the user details of %s", user);
    return INVALID_USER_NAME;
  }

  gid_t tasktracker_gid = getegid(); // the group permissions of the binary.

  char **local_dir = (char **) get_values(TT_SYS_DIR_KEY);
  if (local_dir == NULL) {
    fprintf(LOGFILE, "%s is not configured.\n", TT_SYS_DIR_KEY);
    cleanup();
    return INVALID_TT_ROOT;
  }

  char *full_local_dir_str = (char *) get_value(TT_SYS_DIR_KEY);
#ifdef DEBUG
  fprintf(LOGFILE, "Value from config for %s is %s.\n", TT_SYS_DIR_KEY,
      full_local_dir_str);
#endif

  int is_tt_user = (user_detail->pw_uid == getuid());
  
  // for tt_user, set 770 permissions; for any other user, set 570 for job-dir
  mode_t permissions = is_tt_user ? (S_IRWXU | S_IRWXG)
                                  : (S_IRUSR | S_IXUSR | S_IRWXG);
  char *job_dir, *job_work_dir;
  char **local_dir_ptr = local_dir;
  int failed = 0;
  while (*local_dir_ptr != NULL) {
    job_dir = get_job_directory(*local_dir_ptr, user, jobid);
    if (job_dir == NULL) {
      fprintf(LOGFILE, "Couldn't get job directory for %s.\n", jobid);
      failed = 1;
      break;
    }

    struct stat filestat;
    if (stat(job_dir, &filestat) != 0) {
      if (errno == ENOENT) {
#ifdef DEBUG
        fprintf(LOGFILE, "job_dir %s doesn't exist. Not doing anything.\n",
            job_dir);
#endif
      } else {
        // stat failed because of something else!
        fprintf(LOGFILE, "Failed to stat the job_dir %s\n", job_dir);
        failed = 1;
        free(job_dir);
        break;
      }
    } else if (secure_path(job_dir, user_detail->pw_uid, tasktracker_gid,
               permissions, S_ISGID | permissions, 1) != 0) {
      // No setgid on files and setgid on dirs,
      // 770 for tt_user and 570 for any other user
      fprintf(LOGFILE, "Failed to secure the job_dir %s\n", job_dir);
      failed = 1;
      free(job_dir);
      break;
    } else if (!is_tt_user) {
      // For tt_user, we don't need this as we already set 2770 for
      // job-work-dir because of "chmod -R" done above
      job_work_dir = get_job_work_directory(job_dir);
      if (job_work_dir == NULL) {
        fprintf(LOGFILE, "Couldn't get job-work directory for %s.\n", jobid);
        failed = 1;
        break;
      }

      // Set 2770 on the job-work directory
      if (stat(job_work_dir, &filestat) != 0) {
        if (errno == ENOENT) {
#ifdef DEBUG
          fprintf(LOGFILE,
              "job_work_dir %s doesn't exist. Not doing anything.\n",
              job_work_dir);
#endif
          free(job_work_dir);
        } else {
          // stat failed because of something else!
          fprintf(LOGFILE, "Failed to stat the job_work_dir %s\n",
              job_work_dir);
          failed = 1;
          free(job_work_dir);
          free(job_dir);
          break;
        }
      } else if (change_mode(job_work_dir, S_ISGID | S_IRWXU | S_IRWXG) != 0) {
        fprintf(LOGFILE,
            "couldn't change the permissions of job_work_dir %s\n",
            job_work_dir);
        failed = 1;
        free(job_work_dir);
        free(job_dir);
        break;
      }
    }

    local_dir_ptr++;
    free(job_dir);
  }
  free(local_dir);
  free(full_local_dir_str);
  int exit_code = 0;
  if (failed) {
    exit_code = INITIALIZE_JOB_FAILED;
    goto cleanup;
  }

  char *log_dir = (char *) get_value(TT_LOG_DIR_KEY);
  if (log_dir == NULL) {
    fprintf(LOGFILE, "Log directory is not configured.\n");
    exit_code = INVALID_TT_LOG_DIR;
    goto cleanup;
  }

  if (prepare_job_logs(log_dir, jobid, permissions) != 0) {
    fprintf(LOGFILE, "Couldn't prepare job logs directory %s for %s.\n",
        log_dir, jobid);
    exit_code = PREPARE_JOB_LOGS_FAILED;
  }

  cleanup:
  // free configurations
  cleanup();
  if (log_dir != NULL) {
    free(log_dir);
  }
  return exit_code;
}

/**
 * Function to initialize the distributed cache file for a user.
 * It does the following:
 *     *  sudo chown user:mapred -R taskTracker/$user/distcache/<randomdir>
 *     *  if user is not $tt_user,
 *     *    sudo chmod 2570 -R taskTracker/$user/distcache/<randomdir>
 *     *  else // user is tt_user
 *     *    sudo chmod 2770 -R taskTracker/$user/distcache/<randomdir>
 * This is done once per localization. Tasks reusing JVMs just create
 * symbolic links themselves and so there isn't anything specific to do in
 * that case.
 */
int initialize_distributed_cache_file(const char *tt_root, 
    const char *unique_string, const char *user) {
  if (tt_root == NULL) {
    fprintf(LOGFILE, "tt_root passed is null.\n");
    return INVALID_ARGUMENT_NUMBER;
  }
  if (unique_string == NULL) {
    fprintf(LOGFILE, "unique_string passed is null.\n");
    return INVALID_ARGUMENT_NUMBER;
  }
 
  if (user == NULL) {
    fprintf(LOGFILE, "user passed is null.\n");
    return INVALID_ARGUMENT_NUMBER;
  }

  if (get_user_details(user) < 0) {
    fprintf(LOGFILE, "Couldn't get the user details of %s", user);
    return INVALID_USER_NAME;
  }
  //Check tt_root
  if (check_tt_root(tt_root) < 0) {
    fprintf(LOGFILE, "invalid tt root passed %s\n", tt_root);
    cleanup();
    return INVALID_TT_ROOT;
  }

  // set permission on the unique directory
  char *localized_unique_dir = get_distributed_cache_directory(tt_root, user,
      unique_string);
  if (localized_unique_dir == NULL) {
    fprintf(LOGFILE, "Couldn't get unique distcache directory for %s.\n", user);
    cleanup();
    return INITIALIZE_DISTCACHEFILE_FAILED;
  }

  gid_t binary_gid = getegid(); // the group permissions of the binary.
  
  int is_tt_user = (user_detail->pw_uid == getuid());
  
  // for tt_user, set 770 permissions; for any other user, set 570
  mode_t permissions = is_tt_user ? (S_IRWXU | S_IRWXG)
                                  : (S_IRUSR | S_IXUSR | S_IRWXG);
  int failed = 0;
  struct stat filestat;
  if (stat(localized_unique_dir, &filestat) != 0) {
    // stat on distcache failed because of something
    fprintf(LOGFILE, "Failed to stat the localized_unique_dir %s\n",
        localized_unique_dir);
    failed = INITIALIZE_DISTCACHEFILE_FAILED;
  } else if (secure_path(localized_unique_dir, user_detail->pw_uid,
        binary_gid, permissions, S_ISGID | permissions, 1) != 0) {
    // No setgid on files and setgid on dirs,
    // 770 for tt_user and 570 for any other user
    fprintf(LOGFILE, "Failed to secure the localized_unique_dir %s\n",
        localized_unique_dir);
    failed = INITIALIZE_DISTCACHEFILE_FAILED;
  }
  free(localized_unique_dir);
  cleanup();
  return failed;
}

/**
 * Function used to initialize task. Prepares attempt_dir, jars_dir and
 * log_dir to be accessible by the child
 */
int initialize_task(const char *jobid, const char *taskid, const char *user) {
  int exit_code = 0;
#ifdef DEBUG
  fprintf(LOGFILE, "job-id passed to initialize_task : %s.\n", jobid);
  fprintf(LOGFILE, "task-d passed to initialize_task : %s.\n", taskid);
#endif

  if (prepare_attempt_directories(jobid, taskid, user) != 0) {
    fprintf(LOGFILE,
        "Couldn't prepare the attempt directories for %s of user %s.\n",
        taskid, user);
    exit_code = PREPARE_ATTEMPT_DIRECTORIES_FAILED;
    goto cleanup;
  }

  char *log_dir = (char *) get_value(TT_LOG_DIR_KEY);
  if (log_dir == NULL) {
    fprintf(LOGFILE, "Log directory is not configured.\n");
    exit_code = INVALID_TT_LOG_DIR;
    goto cleanup;
  }

  if (prepare_task_logs(log_dir, jobid, taskid) != 0) {
    fprintf(LOGFILE, "Couldn't prepare task logs directory %s for %s.\n",
        log_dir, taskid);
    exit_code = PREPARE_TASK_LOGS_FAILED;
  }

  cleanup:
  // free configurations
  cleanup();
  if (log_dir != NULL) {
    free(log_dir);
  }
  return exit_code;
}

/*
 * Function used to launch a task as the provided user.
 */
int run_task_as_user(const char * user, const char *jobid, const char *taskid,
    const char *tt_root) {
  return run_process_as_user(user, jobid, taskid, tt_root, LAUNCH_TASK_JVM);
}

/*
 * Function that is used as a helper to launch task JVMs and debug scripts.
 * Not meant for launching any other process. It does the following :
 * 1) Checks if the tt_root passed is found in mapreduce.cluster.local.dir
 * 2) Prepares attempt_dir and log_dir to be accessible by the task JVMs
 * 3) Uses get_task_launcher_file to fetch the task script file path
 * 4) Does an execlp on the same in order to replace the current image with
 * task image.
 */
int run_process_as_user(const char * user, const char * jobid, 
const char *taskid, const char *tt_root, int command) {
  if (command != LAUNCH_TASK_JVM && command != RUN_DEBUG_SCRIPT) {
    return INVALID_COMMAND_PROVIDED;
  }
  if (jobid == NULL || taskid == NULL || tt_root == NULL) {
    return INVALID_ARGUMENT_NUMBER;
  }
  
  if (command == LAUNCH_TASK_JVM) {
    fprintf(LOGFILE, "run_process_as_user launching a JVM for task :%s.\n", taskid);
  } else if (command == RUN_DEBUG_SCRIPT) {
    fprintf(LOGFILE, "run_process_as_user launching a debug script for task :%s.\n", taskid);
  }

#ifdef DEBUG
  fprintf(LOGFILE, "Job-id passed to run_process_as_user : %s.\n", jobid);
  fprintf(LOGFILE, "task-d passed to run_process_as_user : %s.\n", taskid);
  fprintf(LOGFILE, "tt_root passed to run_process_as_user : %s.\n", tt_root);
#endif

  //Check tt_root before switching the user, as reading configuration
  //file requires privileged access.
  if (check_tt_root(tt_root) < 0) {
    fprintf(LOGFILE, "invalid tt root passed %s\n", tt_root);
    cleanup();
    return INVALID_TT_ROOT;
  }

  int exit_code = 0;
  char *job_dir = NULL, *task_script_path = NULL;

  if (command == LAUNCH_TASK_JVM && 
     (exit_code = initialize_task(jobid, taskid, user)) != 0) {
    fprintf(LOGFILE, "Couldn't initialise the task %s of user %s.\n", taskid,
        user);
    goto cleanup;
  }

  job_dir = get_job_directory(tt_root, user, jobid);
  if (job_dir == NULL) {
    fprintf(LOGFILE, "Couldn't obtain job_dir for %s in %s.\n", jobid, tt_root);
    exit_code = OUT_OF_MEMORY;
    goto cleanup;
  }

  task_script_path = get_task_launcher_file(job_dir, taskid);
  if (task_script_path == NULL) {
    fprintf(LOGFILE, "Couldn't obtain task_script_path in %s.\n", job_dir);
    exit_code = OUT_OF_MEMORY;
    goto cleanup;
  }

  errno = 0;
  exit_code = check_path_for_relative_components(task_script_path);
  if(exit_code != 0) {
    goto cleanup;
  }

  //change the user
  fcloseall();
  free(job_dir);
  umask(0007);
  if (change_user(user) != 0) {
    exit_code = SETUID_OPER_FAILED;
    goto cleanup;
  }

  errno = 0;
  cleanup();
  execlp(task_script_path, task_script_path, NULL);
  if (errno != 0) {
    free(task_script_path);
    if (command == LAUNCH_TASK_JVM) {
      fprintf(LOGFILE, "Couldn't execute the task jvm file: %s", strerror(errno));
      exit_code = UNABLE_TO_EXECUTE_TASK_SCRIPT;
    } else if (command == RUN_DEBUG_SCRIPT) {
      fprintf(LOGFILE, "Couldn't execute the task debug script file: %s", strerror(errno));
      exit_code = UNABLE_TO_EXECUTE_DEBUG_SCRIPT;
    }
  }

  return exit_code;

cleanup:
  if (job_dir != NULL) {
    free(job_dir);
  }
  if (task_script_path != NULL) {
    free(task_script_path);
  }
  // free configurations
  cleanup();
  return exit_code;
}
/*
 * Function used to launch a debug script as the provided user. 
 */
int run_debug_script_as_user(const char * user, const char *jobid, const char *taskid,
    const char *tt_root) {
  return run_process_as_user(user, jobid, taskid, tt_root, RUN_DEBUG_SCRIPT);
}
/**
 * Function used to terminate/kill a task launched by the user,
 * or dump the process' stack (by sending SIGQUIT).
 * The function sends appropriate signal to the process group
 * specified by the task_pid.
 */
int kill_user_task(const char *user, const char *task_pid, int sig) {
  int pid = 0;

  if(task_pid == NULL) {
    return INVALID_ARGUMENT_NUMBER;
  }

#ifdef DEBUG
  fprintf(LOGFILE, "user passed to kill_user_task : %s.\n", user);
  fprintf(LOGFILE, "task-pid passed to kill_user_task : %s.\n", task_pid);
  fprintf(LOGFILE, "signal passed to kill_user_task : %d.\n", sig);
#endif

  pid = atoi(task_pid);

  if(pid <= 0) {
    return INVALID_TASK_PID;
  }

  fcloseall();
  if (change_user(user) != 0) {
    cleanup();
    return SETUID_OPER_FAILED;
  }

  //Don't continue if the process-group is not alive anymore.
  if(kill(-pid,0) < 0) {
    errno = 0;
    cleanup();
    return 0;
  }

  if (kill(-pid, sig) < 0) {
    if(errno != ESRCH) {
      fprintf(LOGFILE, "Error is %s\n", strerror(errno));
      cleanup();
      return UNABLE_TO_KILL_TASK;
    }
    errno = 0;
  }
  cleanup();
  return 0;
}

/**
 * Enables the path for deletion by changing the owner, group and permissions
 * of the specified path and all the files/directories in the path recursively.
 *     *  sudo chown user:mapred -R full_path
 *     *  sudo chmod 2770 -R full_path
 * Before changing permissions, makes sure that the given path doesn't contain
 * any relative components.
 * tt_root : is the base path(i.e. mapred-local-dir) sent to task-controller
 * full_path : is either jobLocalDir, taskDir OR taskWorkDir that is to be 
 *             deleted
 */
static int enable_path_for_cleanup(const char *tt_root, const char *user,
                                   char *full_path) {
  int exit_code = 0;
  gid_t tasktracker_gid = getegid(); // the group permissions of the binary.

  if (check_tt_root(tt_root) < 0) {
    fprintf(LOGFILE, "invalid tt root passed %s\n", tt_root);
    cleanup();
    return INVALID_TT_ROOT;
  }
 
  if (full_path == NULL) {
    fprintf(LOGFILE,
            "Could not build the full path. Not deleting the dir %s\n",
            full_path);
    exit_code = UNABLE_TO_BUILD_PATH; // may be malloc failed
  }
     // Make sure that the path given is not having any relative components
  else if ((exit_code = check_path_for_relative_components(full_path)) != 0) {
    fprintf(LOGFILE,
    "Not changing permissions. Path may contain relative components.\n",
         full_path);
  }
  else if (get_user_details(user) < 0) {
    fprintf(LOGFILE, "Couldn't get the user details of %s.\n", user);
    exit_code = INVALID_USER_NAME;
  }
  else if (exit_code = secure_path(full_path, user_detail->pw_uid,
               tasktracker_gid,
               S_IRWXU | S_IRWXG, S_ISGID | S_IRWXU | S_IRWXG, 0) != 0) {
    // No setgid on files and setgid on dirs, 770.
    // set 770 permissions for user, TTgroup for all files/directories in
    // 'full_path' recursively sothat deletion of path by TaskTracker succeeds.

    fprintf(LOGFILE, "Failed to set permissions for %s\n", full_path);
  }

  if (full_path != NULL) {
    free(full_path);
  }
  // free configurations
  cleanup();
  return exit_code;
}

/**
 * Enables the task work-dir/local-dir path for deletion.
 * tt_root : is the base path(i.e. mapred-local-dir) sent to task-controller
 * dir_to_be_deleted : is either taskDir OR taskWorkDir that is to be deleted
 */
int enable_task_for_cleanup(const char *tt_root, const char *user,
           const char *jobid, const char *dir_to_be_deleted) {
  char *full_path = get_task_dir_path(tt_root, user, jobid, dir_to_be_deleted);
  return enable_path_for_cleanup(tt_root, user, full_path);
}

/**
 * Enables the jobLocalDir for deletion.
 * tt_root : is the base path(i.e. mapred-local-dir) sent to task-controller
 * user    : owner of the job
 * jobid   : id of the job for which the cleanup is needed.
 */
int enable_job_for_cleanup(const char *tt_root, const char *user, 
                           const char *jobid) {
  char *full_path = get_job_directory(tt_root, user, jobid);
  return enable_path_for_cleanup(tt_root, user, full_path);
}
