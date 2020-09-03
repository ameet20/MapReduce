#include "test.hh"

int main()
{
  org::apache::hadoop::record::test::RecRecord1 r1;
  org::apache::hadoop::record::test::RecRecord1 r2;
  r1.setBoolVal(true);
  r1.setByteVal((int8_t)0x66);
  r1.setFloatVal(3.145);
  r1.setDoubleVal(1.5234);
  r1.setIntVal(4567);
  r1.setLongVal(0x5a5a5a5a5a5aLL);
  std::string& s = r1.getStringVal();
  s = "random text";
  {
    hadoop::FileInStream istream;
    istream.open("/tmp/hadooptemp.dat");
    hadoop::RecordReader reader(istream, hadoop::kBinary);
    reader.read(r2);
    if (r1 == r2) {
      printf("Binary archive test passed.\n");
    } else {
      printf("Binary archive test failed.\n");
    }
    istream.close();
  }
  {
    hadoop::FileInStream istream;
    istream.open("/tmp/hadooptemp.txt");
    hadoop::RecordReader reader(istream, hadoop::kCSV);
    reader.read(r2);
    if (r1 == r2) {
      printf("CSV archive test passed.\n");
    } else {
      printf("CSV archive test failed.\n");
    }
    istream.close();
  }
  {
    hadoop::FileInStream istream;
    istream.open("/tmp/hadooptemp.xml");
    hadoop::RecordReader reader(istream, hadoop::kXML);
    reader.read(r2);
    if (r1 == r2) {
      printf("XML archive test passed.\n");
    } else {
      printf("XML archive test failed.\n");
    }
    istream.close();
  }
  return 0;
}
