ACLOCAL_AMFLAGS = -I ../utils/m4
AM_CXXFLAGS=-I$(srcdir)/api -Wall -I$(HADOOP_UTILS_PREFIX)/include

# List the api header files and where they will be installed
apidir = $(includedir)/hadoop
api_HEADERS = \
	api/hadoop/Pipes.hh \
	api/hadoop/TemplateFactory.hh

# Define the libaries that need to be built
lib_LIBRARIES = libhadooppipes.a

# Define the sources for lib 
libhadooppipes_a_SOURCES = \
	impl/HadoopPipes.cc
