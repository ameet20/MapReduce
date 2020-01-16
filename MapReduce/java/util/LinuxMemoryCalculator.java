package org.apache.hadoop.util;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Plugin to calculate virtual and physical memories on Linux systems.
 * 
 * @deprecated Use
 *             {@link org.apache.hadoop.mapreduce.util.LinuxMemoryCalculatorPlugin}
 *             instead
 */
@Deprecated
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class LinuxMemoryCalculatorPlugin extends
    org.apache.hadoop.mapreduce.util.LinuxMemoryCalculatorPlugin {
  // Inherits everything from the super class
}
