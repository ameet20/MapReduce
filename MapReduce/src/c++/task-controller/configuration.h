#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>

#define INCREMENT_SIZE 1000
#define MAX_SIZE 10

struct confentry {
  const char *key;
  const char *value;
};


struct configuration {
  int size;
  struct confentry **confdetails;
};

FILE *LOGFILE;

#ifdef HADOOP_CONF_DIR
  #define CONF_FILE_PATTERN "%s/taskcontroller.cfg"
#else
  #define CONF_FILE_PATTERN "%s/conf/taskcontroller.cfg"
#endif

extern struct configuration config;
//configuration file contents
#ifndef HADOOP_CONF_DIR
  extern char *hadoop_conf_dir;
#endif
//method exposed to get the configurations
const char * get_value(const char* key);
//method to free allocated configuration
void free_configurations();

//function to return array of values pointing to the key. Values are
//comma seperated strings.
const char ** get_values(const char* key);
