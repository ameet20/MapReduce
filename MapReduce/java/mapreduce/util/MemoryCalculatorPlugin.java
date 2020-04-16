package org.apache.hadoop.mapreduce.util;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * Plugin to calculate virtual and physical memories on the system.
 * @deprecated Use
 *             {@link org.apache.hadoop.mapreduce.util.ResourceCalculatorPlugin}
 *             instead
 */
@Deprecated
@InterfaceAudience.Private
@InterfaceStability.Unstable
public abstract class MemoryCalculatorPlugin extends Configured {

  /**
   * Obtain the total size of the virtual memory present in the system.
   * 
   * @return virtual memory size in bytes.
   */
  public abstract long getVirtualMemorySize();

  /**
   * Obtain the total size of the physical memory present in the system.
   * 
   * @return physical memory size bytes.
   */
  public abstract long getPhysicalMemorySize();

  /**
   * Get the MemoryCalculatorPlugin from the class name and configure it. If
   * class name is null, this method will try and return a memory calculator
   * plugin available for this system.
   * 
   * @param clazz class-name
   * @param conf configure the plugin with this.
   * @return MemoryCalculatorPlugin
   */
  public static MemoryCalculatorPlugin getMemoryCalculatorPlugin(
      Class<? extends MemoryCalculatorPlugin> clazz, Configuration conf) {

    if (clazz != null) {
      return ReflectionUtils.newInstance(clazz, conf);
    }

    // No class given, try a os specific class
    try {
      String osName = System.getProperty("os.name");
      if (osName.startsWith("Linux")) {
        return new LinuxMemoryCalculatorPlugin();
      }
    } catch (SecurityException se) {
      // Failed to get Operating System name.
      return null;
    }

    // Not supported on this system.
    return null;
  }
}
