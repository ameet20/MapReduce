package org.apache.hadoop.mapreduce.util;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * Plugin to calculate resource information on the system.
 * 
 */
@InterfaceAudience.Private
@InterfaceStability.Unstable
public abstract class ResourceCalculatorPlugin extends Configured {

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
   * Obtain the total size of the available virtual memory present
   * in the system.
   *
   * @return available virtual memory size in bytes.
   */
  public abstract long getAvailableVirtualMemorySize();

  /**
   * Obtain the total size of the available physical memory present
   * in the system.
   *
   * @return available physical memory size bytes.
   */
  public abstract long getAvailablePhysicalMemorySize();

  /**
   * Obtain the total number of processors present on the system.
   *
   * @return number of processors
   */
  public abstract int getNumProcessors();

  /**
   * Obtain the CPU frequency of on the system.
   *
   * @return CPU frequency in kHz
   */
  public abstract long getCpuFrequency();

  /**
   * Obtain the cumulative CPU time since the system is on.
   *
   * @return cumulative CPU time in milliseconds
   */
  public abstract long getCumulativeCpuTime();

  /**
   * Obtain the CPU usage % of the machine. Return -1 if it is unavailable
   *
   * @return CPU usage in %
   */
  public abstract float getCpuUsage();

  /**
   * Obtain resource status used by current process tree.
   */
  @InterfaceAudience.Private
  @InterfaceStability.Unstable
  public abstract ProcResourceValues getProcResourceValues();

  public class ProcResourceValues {
    private final long cumulativeCpuTime;
    private final long physicalMemorySize;
    private final long virtualMemorySize;
    public ProcResourceValues(long cumulativeCpuTime, long physicalMemorySize,
                              long virtualMemorySize) {
      this.cumulativeCpuTime = cumulativeCpuTime;
      this.physicalMemorySize = physicalMemorySize;
      this.virtualMemorySize = virtualMemorySize;
    }
    /**
     * Obtain the physical memory size used by current process tree.
     * @return physical memory size in bytes.
     */
    public long getPhysicalMemorySize() {
      return physicalMemorySize;
    }

    /**
     * Obtain the virtual memory size used by a current process tree.
     * @return virtual memory size in bytes.
     */
    public long getVirtualMemorySize() {
      return virtualMemorySize;
    }

    /**
     * Obtain the cumulative CPU time used by a current process tree.
     * @return cumulative CPU time in milliseconds
     */
    public long getCumulativeCpuTime() {
      return cumulativeCpuTime;
    }
  }

  /**
   * Get the ResourceCalculatorPlugin from the class name and configure it. If
   * class name is null, this method will try and return a memory calculator
   * plugin available for this system.
   * 
   * @param clazz class-name
   * @param conf configure the plugin with this.
   * @return ResourceCalculatorPlugin
   */
  public static ResourceCalculatorPlugin getResourceCalculatorPlugin(
      Class<? extends ResourceCalculatorPlugin> clazz, Configuration conf) {

    if (clazz != null) {
      return ReflectionUtils.newInstance(clazz, conf);
    }

    // No class given, try a os specific class
    try {
      String osName = System.getProperty("os.name");
      if (osName.startsWith("Linux")) {
        return new LinuxResourceCalculatorPlugin();
      }
    } catch (SecurityException se) {
      // Failed to get Operating System name.
      return null;
    }

    // Not supported on this system.
    return null;
  }
}
