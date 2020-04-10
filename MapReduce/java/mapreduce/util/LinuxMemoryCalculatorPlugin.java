package org.apache.hadoop.mapreduce.util;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Plugin to calculate virtual and physical memories on Linux systems.
 * @deprecated 
 * Use {@link org.apache.hadoop.mapreduce.util.LinuxResourceCalculatorPlugin}
 * instead
 */
@Deprecated
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class LinuxMemoryCalculatorPlugin extends MemoryCalculatorPlugin {
  private LinuxResourceCalculatorPlugin resourceCalculatorPlugin;
  // Use everything from LinuxResourceCalculatorPlugin
  public LinuxMemoryCalculatorPlugin() {
    resourceCalculatorPlugin = new LinuxResourceCalculatorPlugin();
  }
  
  /** {@inheritDoc} */
  @Override
  public long getPhysicalMemorySize() {
    return resourceCalculatorPlugin.getPhysicalMemorySize();
  }
  
  /** {@inheritDoc} */
  @Override
  public long getVirtualMemorySize() {
    return resourceCalculatorPlugin.getVirtualMemorySize();
  }
}
