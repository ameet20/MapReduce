#ifndef UTILS_HH_
#define UTILS_HH_

#include "recordio.hh"
#include "typeIDs.hh"


namespace hadoop {

  /**
   * Various utility functions for Hadooop record I/O platform.
   */

class Utils {

private: 
  /** Cannot create a new instance of Utils */
  Utils() {};

public: 

  /**
   * read/skip bytes from stream based on a type
   */
  static void skip(IArchive& a, const char* tag, const TypeID& typeID);

};


}
#endif // UTILS_HH_
