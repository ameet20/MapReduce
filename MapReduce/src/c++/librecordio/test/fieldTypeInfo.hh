#ifndef FIELDTYPEINFO_HH_
#define FIELDTYPEINFO_HH_

#include "recordio.hh"
#include "typeIDs.hh"

namespace hadoop {

class TypeID;

/** 
 * Represents a type information for a field, which is made up of its 
 * ID (name) and its type (a TypeID object).
 */
class FieldTypeInfo {
  
private: 
  // we own memory mgmt of these vars
  const std::string* pFieldID;
  const TypeID* pTypeID;

public: 
  FieldTypeInfo(const std::string* pFieldID, const TypeID* pTypeID) : 
    pFieldID(pFieldID), pTypeID(pTypeID) {}
  FieldTypeInfo(const FieldTypeInfo& ti);
  virtual ~FieldTypeInfo();

  const TypeID* getTypeID() const {return pTypeID;}
  const std::string* getFieldID() const {return pFieldID;}
  void serialize(::hadoop::OArchive& a_, const char* tag) const;
  bool operator==(const FieldTypeInfo& peer_) const;
  FieldTypeInfo* clone() const {return new FieldTypeInfo(*this);}

  void print(int space=0) const;

};

}

#endif // FIELDTYPEINFO_HH_
