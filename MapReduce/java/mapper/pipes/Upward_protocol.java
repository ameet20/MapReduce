package org.apache.hadoop.mapred.pipes;

import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

/**
 * The interface for the messages that can come up from the child. All of these
 * calls are asynchronous and return before the message has been processed.
 */
interface UpwardProtocol<K extends WritableComparable, V extends Writable> {
  /**
   * Output a record from the child.
   * @param key the record's key
   * @param value the record's value
   * @throws IOException
   */
  void output(K key, V value) throws IOException;
  
  /**
   * Map functions where the application has defined a partition function
   * output records along with their partition.
   * @param reduce the reduce to send this record to
   * @param key the record's key
   * @param value the record's value
   * @throws IOException
   */
  void partitionedOutput(int reduce, K key, 
                         V value) throws IOException;
  
  /**
   * Update the task's status message
   * @param msg the string to display to the user
   * @throws IOException
   */
  void status(String msg) throws IOException;
  
  /**
   * Report making progress (and the current progress)
   * @param progress the current progress (0.0 to 1.0)
   * @throws IOException
   */
  void progress(float progress) throws IOException;
  
  /**
   * Report that the application has finished processing all inputs 
   * successfully.
   * @throws IOException
   */
  void done() throws IOException;
  
  /**
   * Report that the application or more likely communication failed.
   * @param e
   */
  void failed(Throwable e);
  
  /**
   * Register a counter with the given id and group/name.
   * @param group counter group
   * @param name counter name
   * @throws IOException
   */
  void registerCounter(int id, String group, String name) throws IOException;
  
  /**
   * Increment the value of a registered counter.
   * @param id counter id of the registered counter
   * @param amount increment for the counter value
   * @throws IOException
   */
  void incrementCounter(int id, long amount) throws IOException;

  /**
   * Handles authentication response from client.
   * It must notify the threads waiting for authentication response.
   * @param digest
   * @return true if authentication is successful
   * @throws IOException
   */
  boolean authenticate(String digest) throws IOException;

}
