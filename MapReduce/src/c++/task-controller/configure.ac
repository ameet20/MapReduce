AC_PREREQ(2.59)
AC_INIT([task-controller],[0.1])

#changing default prefix value to empty string, so that binary does not
#gets installed within system
AC_PREFIX_DEFAULT(.)

#add new argument called -with-confdir
AC_ARG_WITH(confdir,[--with-confdir path to hadoop conf dir])
AC_CONFIG_SRCDIR([task-controller.h])
AC_CONFIG_AUX_DIR([config])
AC_CONFIG_MACRO_DIR([../utils/m4])
AM_INIT_AUTOMAKE([subdir-objects foreign no-dist])

# Checks for programs.
AC_PROG_CC

# Checks for libraries.

# Checks for header files.
AC_HEADER_STDC
AC_CHECK_HEADERS([stdlib.h string.h unistd.h fcntl.h])

#check for HADOOP_CONF_DIR


if test "$with_confdir" != ""
then
AC_DEFINE_UNQUOTED(HADOOP_CONF_DIR, ["$with_confdir"], [Location of Hadoop configuration])
fi
# Checks for typedefs, structures, and compiler characteristics.
AC_C_CONST
AC_TYPE_PID_T
AC_TYPE_MODE_T
AC_TYPE_SIZE_T

# Checks for library functions.
AC_FUNC_MALLOC
AC_FUNC_REALLOC
AC_FUNC_CHOWN
AC_CHECK_FUNCS([strerror memset mkdir rmdir strdup])

AC_CONFIG_FILES([Makefile])
AC_OUTPUT

AC_HEADER_STDBOOL
AC_PROG_MAKE_SET
