package org.apache.hadoop.util;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * A Proc file-system based ProcessTree. Works only on Linux.
 * 
 * @deprecated Use
 *             {@link org.apache.hadoop.mapreduce.util.ProcfsBasedProcessTree}
 *             instead
 */
@Deprecated
@InterfaceAudience.Private
@InterfaceStability.Unstable
public class ProcfsBasedProcessTree extends
    org.apache.hadoop.mapreduce.util.ProcfsBasedProcessTree {

  public ProcfsBasedProcessTree(String pid) {
    super(pid);
  }

  public ProcfsBasedProcessTree(String pid, boolean setsidUsed,
      long sigkillInterval) {
    super(pid, setsidUsed, sigkillInterval);
  }

  public ProcfsBasedProcessTree(String pid, boolean setsidUsed,
      long sigkillInterval, String procfsDir) {
    super(pid, setsidUsed, sigkillInterval, procfsDir);
  }

  public ProcfsBasedProcessTree getProcessTree() {
    return (ProcfsBasedProcessTree) super.getProcessTree();
  }
}
