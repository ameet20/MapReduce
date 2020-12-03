package org.apache.hadoop.contrib.utils.join;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class provides an implementation of ResetableIterator. The
 * implementation will be based on ArrayList.
 * 
 * 
 */
public class ArrayListBackedIterator implements ResetableIterator {

  private Iterator iter;

  private ArrayList<Object> data;

  public ArrayListBackedIterator() {
    this(new ArrayList<Object>());
  }

  public ArrayListBackedIterator(ArrayList<Object> data) {
    this.data = data;
    this.iter = this.data.iterator();
  }

  public void add(Object item) {
    this.data.add(item);
  }

  public boolean hasNext() {
    return this.iter.hasNext();
  }

  public Object next() {
    return this.iter.next();
  }

  public void remove() {

  }

  public void reset() {
    this.iter = this.data.iterator();
  }

  public void close() throws IOException {
    this.iter = null;
    this.data = null;
  }
}
