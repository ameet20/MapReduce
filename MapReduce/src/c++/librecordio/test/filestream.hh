#ifndef FILESTREAM_HH_
#define FILESTREAM_HH_

#include <stdio.h>
#include <stdint.h>
#include <string>
#include "recordio.hh"

namespace hadoop {

class FileInStream : public InStream {
public:
  FileInStream();
  bool open(const std::string& name);
  ssize_t read(void *buf, size_t buflen);
  bool skip(size_t nbytes);
  bool close();
  virtual ~FileInStream();
private:
  FILE *mFile;
};


class FileOutStream: public OutStream {
public:
  FileOutStream();
  bool open(const std::string& name, bool overwrite);
  ssize_t write(const void* buf, size_t len);
  bool advance(size_t nbytes);
  bool close();
  virtual ~FileOutStream();
private:
  FILE *mFile;
};

}; // end namespace
#endif /*FILESTREAM_HH_*/
