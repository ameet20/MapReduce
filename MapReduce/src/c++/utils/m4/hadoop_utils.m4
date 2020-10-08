AC_DEFUN([CHECK_INSTALL_CFLAG],[
AC_REQUIRE([AC_PROG_INSTALL])
touch foo
if $INSTALL -C foo bar; then
  INSTALL_DATA="$INSTALL_DATA -C"
fi
rm -f foo bar
])

# Set up the things we need for compiling hadoop utils
AC_DEFUN([HADOOP_UTILS_SETUP],[
AC_REQUIRE([AC_GNU_SOURCE])
AC_REQUIRE([AC_SYS_LARGEFILE])
])

# define a macro for using hadoop utils
AC_DEFUN([USE_HADOOP_UTILS],[
AC_REQUIRE([HADOOP_UTILS_SETUP])
AC_ARG_WITH([hadoop-utils],
            AS_HELP_STRING([--with-hadoop-utils=<dir>],
                           [directory to get hadoop_utils from]),
            [HADOOP_UTILS_PREFIX="$withval"],
            [HADOOP_UTILS_PREFIX="\${prefix}"])
AC_SUBST(HADOOP_UTILS_PREFIX)
])

AC_DEFUN([HADOOP_PIPES_SETUP],[
AC_CHECK_HEADERS([pthread.h], [], 
  AC_MSG_ERROR(Please check if you have installed the pthread library)) 
AC_CHECK_LIB([pthread], [pthread_create], [], 
  AC_MSG_ERROR(Cannot find libpthread.so, please check))
AC_CHECK_LIB([ssl], [HMAC_Init], [], 
  AC_MSG_ERROR(Cannot find libssl.so, please check))
])

# define a macro for using hadoop pipes
AC_DEFUN([USE_HADOOP_PIPES],[
AC_REQUIRE([USE_HADOOP_UTILS])
AC_REQUIRE([HADOOP_PIPES_SETUP])
AC_ARG_WITH([hadoop-pipes],
            AS_HELP_STRING([--with-hadoop-pipes=<dir>],
                           [directory to get hadoop pipes from]),
            [HADOOP_PIPES_PREFIX="$withval"],
            [HADOOP_PIPES_PREFIX="\${prefix}"])
AC_SUBST(HADOOP_PIPES_PREFIX)
])
