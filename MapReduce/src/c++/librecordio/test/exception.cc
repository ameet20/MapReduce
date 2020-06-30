#include "exception.hh"
#ifdef USE_EXECINFO
#include <execinfo.h>
#endif

#include <errno.h>
#include <sstream>
#include <typeinfo>

using std::string;

namespace hadoop {

  /**
   * Create an exception.
   * @param message The message to give to the user.
   * @param reason The exception that caused the new exception.
   */
  Exception::Exception(const string& message,
                       const string& component,
                       const string& location,
                       const Exception* reason
                       ): mMessage(message),
                          mComponent(component),
                          mLocation(location),
                          mReason(reason)
                          
  {
#ifdef USE_EXECINFO
    mCalls = backtrace(mCallStack, sMaxCallStackDepth);
#else
    mCalls = 0;
#endif
  }

  /**
   * Copy the exception.
   * Clones the reason, if there is one.
   */
  Exception::Exception(const Exception& other
                       ): mMessage(other.mMessage), 
                          mComponent(other.mComponent),
                          mLocation(other.mLocation),
                          mCalls(other.mCalls)
  {
    for(int i=0; i < mCalls; ++i) {
      mCallStack[i] = other.mCallStack[i];
    }
    if (other.mReason) {
      mReason = other.mReason->clone();
    } else {
      mReason = NULL;
    }
  }

  Exception::~Exception() throw () {
    delete mReason;
  }

  /**
   * Print all of the information about the exception.
   */
  void Exception::print(std::ostream& stream) const {
    stream << "Exception " << getTypename();
    if (mComponent.size() != 0) {
      stream << " (" << mComponent << ")";
    }
    stream << ": " << mMessage << "\n";
    if (mLocation.size() != 0) {
      stream << "  thrown at " << mLocation << "\n";
    }
#ifdef USE_EXECINFO
    printCallStack(stream);
#endif
    if (mReason) {
      stream << "caused by: ";
      mReason->print(stream);
    }
    stream.flush();
  }

  /**
   * Result of print() as a string.
   */
  string Exception::toString() const {
    std::ostringstream stream;
    print(stream);
    return stream.str();
}

#ifdef USE_EXECINFO
  /**
   * Print the call stack where the exception was created.
   */
  void Exception::printCallStack(std::ostream& stream) const {
      char ** symbols = backtrace_symbols(mCallStack, mCalls);
      for(int i=0; i < mCalls; ++i) {
        stream << "  ";
        if (i == 0) {
          stream << "at ";
        } else {
          stream << "from ";
        }
        stream << symbols[i] << "\n";
      }
      free(symbols);
  }
#endif

  const char* Exception::getTypename() const {
    return "Exception";
  }

  Exception* Exception::clone() const {
    return new Exception(*this);
  }

  IOException::IOException(const string& message,
                         const string& component,
                         const string& location,
                         const Exception* reason
                         ): Exception(message, component, location, reason) 
  {
  }

  const char* IOException::getTypename() const {
    return "IOException";
  }

  IOException* IOException::clone() const {
    return new IOException(*this);
  }

}
