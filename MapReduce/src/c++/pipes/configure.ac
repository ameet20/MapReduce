AC_PREREQ(2.59)
AC_INIT(hadoop-pipes, 0.13.0, omalley@apache.org)

AC_CONFIG_AUX_DIR([config])
AC_CONFIG_MACRO_DIR([../utils/m4])

AM_INIT_AUTOMAKE([subdir-objects foreign no-dist])

AC_CONFIG_SRCDIR([impl/HadoopPipes.cc])
AC_CONFIG_HEADER([impl/config.h])
AC_CONFIG_FILES([Makefile])

AC_PREFIX_DEFAULT(`pwd`/../install)

USE_HADOOP_UTILS
HADOOP_PIPES_SETUP
CHECK_INSTALL_CFLAG

# Checks for programs.
AC_PROG_CXX
AC_PROG_LIBTOOL

# Checks for libraries.

# Checks for header files.
AC_LANG(C++)
AC_CHECK_HEADERS([unistd.h])

# Checks for typedefs, structures, and compiler characteristics.
AC_HEADER_STDBOOL
AC_C_CONST
AC_TYPE_OFF_T
AC_TYPE_SIZE_T
AC_FUNC_STRERROR_R

# Checks for library functions.
AC_CHECK_FUNCS([mkdir uname])
AC_OUTPUT
