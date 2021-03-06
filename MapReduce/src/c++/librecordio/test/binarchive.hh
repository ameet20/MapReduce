#ifndef BINARCHIVE_HH_
#define BINARCHIVE_HH_

#include "recordio.hh"

namespace hadoop {

class BinIndex : public Index {
private:
  size_t size;
public:
  BinIndex(size_t size_) { size = size_; }
  bool done() { return (size==0); }
  void incr() { size--; }
  ~BinIndex() {}
};
  
class IBinArchive : public IArchive {
private:
  InStream& stream;
public:
  IBinArchive(InStream& _stream) : stream(_stream) {}
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
  virtual ~IBinArchive();
};

class OBinArchive : public OArchive {
private:
  OutStream& stream;
public:
  OBinArchive(OutStream& _stream) : stream(_stream) {}
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
  virtual ~OBinArchive();
};

}
#endif /*BINARCHIVE_HH_*/
