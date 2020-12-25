package org.apache.hadoop.eclipse;

import java.util.logging.Logger;

/**
 * Class to help with debugging properties
 */
public class PropertyTester extends
    org.eclipse.core.expressions.PropertyTester {

  static Logger log = Logger.getLogger(PropertyTester.class.getName());

  public PropertyTester() {
  }

  public boolean test(Object receiver, String property, Object[] args,
      Object expectedValue) {
    log.fine("Test property " + property + ", " + receiver.getClass());

    return true;

    // todo(jz) support test for deployable if module has hadoop nature etc.
  }

}
