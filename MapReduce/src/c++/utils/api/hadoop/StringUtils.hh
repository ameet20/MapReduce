#ifndef HADOOP_STRING_UTILS_HH
#define HADOOP_STRING_UTILS_HH

#include <stdint.h>
#include <string>
#include <vector>

namespace HadoopUtils {

  /**
   * Convert an integer to a string.
   */
  std::string toString(int32_t x);

  /**
   * Convert a string to an integer.
   * @throws Error if the string is not a valid integer
   */
  int32_t toInt(const std::string& val);

  /**
   * Convert the string to a float.
   * @throws Error if the string is not a valid float
   */
  float toFloat(const std::string& val);

  /**
   * Convert the string to a boolean.
   * @throws Error if the string is not a valid boolean value
   */
  bool toBool(const std::string& val);

  /**
   * Get the current time in the number of milliseconds since 1970.
   */
  uint64_t getCurrentMillis();

  /**
   * Split a string into "words". Multiple deliminators are treated as a single
   * word break, so no zero-length words are returned.
   * @param str the string to split
   * @param separator a list of characters that divide words
   */
  std::vector<std::string> splitString(const std::string& str,
                                       const char* separator);

  /**
   * Quote a string to avoid "\", non-printable characters, and the 
   * deliminators.
   * @param str the string to quote
   * @param deliminators the set of characters to always quote
   */
  std::string quoteString(const std::string& str,
                          const char* deliminators);

  /**
   * Unquote the given string to return the original string.
   * @param str the string to unquote
   */
  std::string unquoteString(const std::string& str);

}

#endif
