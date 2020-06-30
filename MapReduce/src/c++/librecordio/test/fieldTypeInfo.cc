#include "fieldTypeInfo.hh"

using namespace hadoop;

FieldTypeInfo::~FieldTypeInfo()
{
  delete pFieldID;
  delete pTypeID;
}

FieldTypeInfo::FieldTypeInfo(const FieldTypeInfo& ti)
{
  pFieldID = new std::string(*ti.pFieldID);
  pTypeID = ti.pTypeID->clone();
}


void FieldTypeInfo::serialize(::hadoop::OArchive& a_, const char* tag) const
{
  a_.serialize(*pFieldID, tag);
  pTypeID->serialize(a_, tag);
}

bool FieldTypeInfo::operator==(const FieldTypeInfo& peer_) const 
{
  // first check if fieldID matches
  if (0 != pFieldID->compare(*(peer_.pFieldID))) {
    return false;
  }
  // now see if typeID matches
  return (*pTypeID == *(peer_.pTypeID));
}

// print out the structure. Helps in debugging.
void FieldTypeInfo::print(int space) const
{
  for (int i=0; i<space; i++) {
    printf(" ");
  }
  printf("FieldTypeInfo(%lx):\n", (long)this);
  for (int i=0; i<space+2; i++) {
    printf(" ");
  }
  printf("field = \"%s\"\n", pFieldID->c_str());
  pTypeID->print(space+2);
}
