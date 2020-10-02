#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <sys/types.h>
#include <pwd.h>
#include <assert.h>
#include <getopt.h>
#include <sys/stat.h>
#include <sys/signal.h>
#include <getopt.h>
#include <grp.h>
#include <dirent.h>
#include <fcntl.h>
#include <fts.h>

#include "configuration.h"

//command definitions
enum command {
  INITIALIZE_USER,
  INITIALIZE_JOB,
  INITIALIZE_DISTRIBUTEDCACHE_FILE,
  LAUNCH_TASK_JVM,
  INITIALIZE_TASK,
  TERMINATE_TASK_JVM,
  KILL_TASK_JVM,
  RUN_DEBUG_SCRIPT,
  SIGQUIT_TASK_JVM,
  ENABLE_TASK_FOR_CLEANUP,
  ENABLE_JOB_FOR_CLEANUP
};

enum errorcodes {
  INVALID_ARGUMENT_NUMBER = 1,
  INVALID_USER_NAME, //2
  INVALID_COMMAND_PROVIDED, //3
  SUPER_USER_NOT_ALLOWED_TO_RUN_TASKS, //4
  INVALID_TT_ROOT, //5
  SETUID_OPER_FAILED, //6
  UNABLE_TO_EXECUTE_TASK_SCRIPT, //7
  UNABLE_TO_KILL_TASK, //8
  INVALID_TASK_PID, //9
  ERROR_RESOLVING_FILE_PATH, //10
  RELATIVE_PATH_COMPONENTS_IN_FILE_PATH, //11
  UNABLE_TO_STAT_FILE, //12
  FILE_NOT_OWNED_BY_TASKTRACKER, //13
  PREPARE_ATTEMPT_DIRECTORIES_FAILED, //14
  INITIALIZE_JOB_FAILED, //15
  PREPARE_TASK_LOGS_FAILED, //16
  INVALID_TT_LOG_DIR, //17
  OUT_OF_MEMORY, //18
  INITIALIZE_DISTCACHEFILE_FAILED, //19
  INITIALIZE_USER_FAILED, //20
  UNABLE_TO_EXECUTE_DEBUG_SCRIPT, //21
  INVALID_CONF_DIR, //22
  UNABLE_TO_BUILD_PATH, //23
  INVALID_TASKCONTROLLER_PERMISSIONS, //24
  PREPARE_JOB_LOGS_FAILED, //25
};

#define USER_DIR_PATTERN "%s/taskTracker/%s"

#define TT_JOB_DIR_PATTERN USER_DIR_PATTERN"/jobcache/%s"

#define USER_DISTRIBUTED_CACHE_DIR_PATTERN USER_DIR_PATTERN"/distcache/%s"

#define JOB_DIR_TO_JOB_WORK_PATTERN "%s/work"

#define JOB_DIR_TO_ATTEMPT_DIR_PATTERN "%s/%s"

#define JOB_LOG_DIR_PATTERN "%s/userlogs/%s"

#define JOB_LOG_DIR_TO_JOB_ACLS_FILE_PATTERN "%s/job-acls.xml"

#define ATTEMPT_LOG_DIR_PATTERN JOB_LOG_DIR_PATTERN"/%s"

#define TASK_SCRIPT_PATTERN "%s/%s/taskjvm.sh"

#define TT_LOCAL_TASK_DIR_PATTERN    "%s/taskTracker/%s/jobcache/%s/%s"

#define TT_SYS_DIR_KEY "mapreduce.cluster.local.dir"

#define TT_LOG_DIR_KEY "hadoop.log.dir"

#define TT_GROUP_KEY "mapreduce.tasktracker.group"

#ifndef HADOOP_CONF_DIR
  #define EXEC_PATTERN "/bin/task-controller"
  extern char * hadoop_conf_dir;
#endif

extern struct passwd *user_detail;

extern FILE *LOGFILE;

int run_task_as_user(const char * user, const char *jobid, const char *taskid,
    const char *tt_root);

int run_debug_script_as_user(const char * user, const char *jobid, const char *taskid,
    const char *tt_root);

int initialize_user(const char *user);

int initialize_task(const char *jobid, const char *taskid, const char *user);

int initialize_job(const char *jobid, const char *user);

int initialize_distributed_cache_file(const char *tt_root, 
    const char* unique_string, const char *user);

int kill_user_task(const char *user, const char *task_pid, int sig);

int enable_task_for_cleanup(const char *tt_root, const char *user,
                            const char *jobid, const char *dir_to_be_deleted);

int enable_job_for_cleanup(const char *tt_root, const char *user,
                           const char *jobid);

int prepare_attempt_directory(const char *attempt_dir, const char *user);

// The following functions are exposed for testing

int check_variable_against_config(const char *config_key,
    const char *passed_value);

int get_user_details(const char *user);

char *get_task_launcher_file(const char *job_dir, const char *attempt_dir);
