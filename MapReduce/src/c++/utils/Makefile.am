ACLOCAL_AMFLAGS = -I m4
AM_CXXFLAGS=-I$(srcdir)/api -Wall

# List the api header files and where they will be installed
apidir = $(includedir)/hadoop
api_HEADERS = \
	api/hadoop/StringUtils.hh \
	api/hadoop/SerialUtils.hh


# Define the libaries that need to be built
lib_LIBRARIES = libhadooputils.a

# Define the sources for lib 
libhadooputils_a_SOURCES = \
	impl/StringUtils.cc \
	impl/SerialUtils.cc
