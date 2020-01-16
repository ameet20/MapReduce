package org.apache.hadoop.util;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Plugin to calculate virtual and physical memories on the system.
 * 
 * @deprecated Use
 *             {@link org.apache.hadoop.mapreduce.util.MemoryCalculatorPlugin}
 *             instead.
 * 
 */
@Deprecated
@InterfaceAudience.Private
@InterfaceStability.Unstable
public abstract class MemoryCalculatorPlugin extends
    org.apache.hadoop.mapreduce.util.MemoryCalculatorPlugin {
}
