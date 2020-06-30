#ifndef CSVARCHIVE_HH_
#define CSVARCHIVE_HH_

#include "recordio.hh"

namespace hadoop {

class PushBackInStream {
private:
  InStream* stream;
  bool isAvail;
  char pbchar;
public:
  void setStream(InStream* stream_) {
    stream = stream_;
    isAvail = false;
    pbchar = 0;
  }
  ssize_t read(void* buf, size_t len) {
    if (len > 0 && isAvail) {
      char* p = (char*) buf;
      *p = pbchar;
      isAvail = false;
      if (len > 1) {
        ssize_t ret = stream->read((char*)buf + 1, len - 1);
        return ret + 1;
      } else {
        return 1;
      }
    } else {
      return stream->read(buf, len);
    }
  }
  void pushBack(char c) {
    pbchar = c;
    isAvail = true;
  }
};

class CsvIndex : public Index {
private:
  PushBackInStream& stream;
public:
  CsvIndex(PushBackInStream& _stream) : stream(_stream) {}
  bool done() {
    char c;
    stream.read(&c, 1);
    if (c != ',') {
      stream.pushBack(c);
    }
    return (c == '}') ? true : false;
  }
  void incr() {}
  ~CsvIndex() {} 
};
  
class ICsvArchive : public IArchive {
private:
  PushBackInStream stream;
public:
  ICsvArchive(InStream& _stream) { stream.setStream(&_stream); }
  virtual void deserialize(int8_t& t, const char* tag);
  virtual void deserialize(bool& t, const char* tag);
  virtual void deserialize(int32_t& t, const char* tag);
  virtual void deserialize(int64_t& t, const char* tag);
  virtual void deserialize(float& t, const char* tag);
  virtual void deserialize(double& t, const char* tag);
  virtual void deserialize(std::string& t, const char* tag);
  virtual void deserialize(std::string& t, size_t& len, const char* tag);
  virtual void startRecord(Record& s, const char* tag);
  virtual void endRecord(Record& s, const char* tag);
  virtual Index* startVector(const char* tag);
  virtual void endVector(Index* idx, const char* tag);
  virtual Index* startMap(const char* tag);
  virtual void endMap(Index* idx, const char* tag);
  virtual ~ICsvArchive();
};

class OCsvArchive : public OArchive {
private:
  OutStream& stream;
  bool isFirst;
  
  void printCommaUnlessFirst() {
    if (!isFirst) {
      stream.write(",",1);
    }
    isFirst = false;
  }
public:
  OCsvArchive(OutStream& _stream) : stream(_stream) {isFirst = true;}
  virtual void serialize(int8_t t, const char* tag);
  virtual void serialize(bool t, const char* tag);
  virtual void serialize(int32_t t, const char* tag);
  virtual void serialize(int64_t t, const char* tag);
  virtual void serialize(float t, const char* tag);
  virtual void serialize(double t, const char* tag);
  virtual void serialize(const std::string& t, const char* tag);
  virtual void serialize(const std::string& t, size_t len, const char* tag);
  virtual void startRecord(const Record& s, const char* tag);
  virtual void endRecord(const Record& s, const char* tag);
  virtual void startVector(size_t len, const char* tag);
  virtual void endVector(size_t len, const char* tag);
  virtual void startMap(size_t len, const char* tag);
  virtual void endMap(size_t len, const char* tag);
  virtual ~OCsvArchive();
};

}
#endif /*CSVARCHIVE_HH_*/
