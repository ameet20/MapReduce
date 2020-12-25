package org.apache.hadoop.eclipse.launch;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class MutexRule implements ISchedulingRule {
  private final String id;

  public MutexRule(String id) {
    this.id = id;
  }

  public boolean contains(ISchedulingRule rule) {
    return (rule instanceof MutexRule) && ((MutexRule) rule).id.equals(id);
  }

  public boolean isConflicting(ISchedulingRule rule) {
    return (rule instanceof MutexRule) && ((MutexRule) rule).id.equals(id);
  }
}
