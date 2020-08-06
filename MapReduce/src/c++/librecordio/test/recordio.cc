#include "recordio.hh"
#include "binarchive.hh"
#include "csvarchive.hh"
#include "xmlarchive.hh"

using namespace hadoop;

hadoop::RecordReader::RecordReader(InStream& stream, RecFormat f)
{
  switch (f) {
    case kBinary:
      mpArchive = new IBinArchive(stream);
      break;
    case kCSV:
      mpArchive = new ICsvArchive(stream);
      break;
    case kXML:
      mpArchive = new IXmlArchive(stream);
      break;
  }
}

hadoop::RecordReader::~RecordReader()
{
  delete mpArchive;
}

void hadoop::RecordReader::read(Record& record)
{
  record.deserialize(*mpArchive, (const char*) NULL);
}

hadoop::RecordWriter::RecordWriter(OutStream& stream, RecFormat f)
{
  switch (f) {
    case kBinary:
      mpArchive = new OBinArchive(stream);
      break;
    case kCSV:
      mpArchive = new OCsvArchive(stream);
      break;
    case kXML:
      mpArchive = new OXmlArchive(stream);
      break;
  }
}

hadoop::RecordWriter::~RecordWriter()
{
  delete mpArchive;
}

void hadoop::RecordWriter::write(const Record& record)
{
  record.serialize(*mpArchive, (const char*) NULL);
}
