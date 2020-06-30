#ifndef EXCEPTION_HH
#define EXCEPTION_HH

#include <exception>
#include <iostream>
#include <string>

namespace hadoop {

  /**
   * Parent-type for all exceptions in hadoop.
   * Provides an application specified message to the user, a call stack from
   * where the exception was created, and optionally an exception that caused 
   * this one.
   */
  class Exception: public std::exception {
  public:

    /**
     * Create an exception.
     * @param message The message to give to the user.
     * @param reason The exception that caused the new exception.
     */
    explicit Exception(const std::string& message,
                       const std::string& component="",
                       const std::string& location="",
                       const Exception* reason=NULL);

    /**
     * Copy the exception.
     * Clones the reason, if there is one.
     */
    Exception(const Exception&);

    virtual ~Exception() throw ();

    /**
     * Make a new copy of the given exception by dynamically allocating
     * memory.
     */
    virtual Exception* clone() const;

    /**
     * Print all of the information about the exception.
     */
    virtual void print(std::ostream& stream=std::cerr) const;

    /**
     * Result of print() as a string.
     */
    virtual std::string toString() const;

#ifdef USE_EXECINFO
    /**
     * Print the call stack where the exception was created.
     */
    virtual void printCallStack(std::ostream& stream=std::cerr) const;
#endif

    const std::string& getMessage() const {
      return mMessage;
    }

    const std::string& getComponent() const {
      return mComponent;
    }

    const std::string& getLocation() const {
      return mLocation;
    }

    const Exception* getReason() const {
      return mReason;
    }

    /**
     * Provide a body for the virtual from std::exception.
     */
    virtual const char* what() const throw () {
      return mMessage.c_str();
    }

    virtual const char* getTypename() const;

  private:
    const static int sMaxCallStackDepth = 10;
    const std::string mMessage;
    const std::string mComponent;
    const std::string mLocation;
    int mCalls;
    void* mCallStack[sMaxCallStackDepth];
    const Exception* mReason;

    // NOT IMPLEMENTED
    std::exception& operator=(const std::exception& right) throw ();
  };

  class IOException: public Exception {
  public:
    IOException(const std::string& message,
                const std::string& component="",
                const std::string& location="",
                const Exception* reason = NULL);

    virtual IOException* clone() const;
    virtual const char* getTypename() const;

  };

}
#endif
