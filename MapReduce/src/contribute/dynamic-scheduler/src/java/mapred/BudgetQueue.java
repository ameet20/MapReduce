package org.apache.hadoop.mapred;

/**
 * Class to hold accounting info about a queue
 * such as remaining budget, spending rate and
 * whether queue usage
 */
public class BudgetQueue {
  String name;
  volatile float budget;
  volatile float spending;
  volatile int used;
  volatile int pending;
  /**
   * @param name queue name
   * @param budget queue budget in credits
   * @param spending queue spending rate in credits per allocation interval
   * to deduct from budget
   */
  public BudgetQueue(String name, float budget, float spending) {
      this.name = name;
      this.budget = budget;
      this.spending = spending;
      this.used = 0;
      this.pending = 0;
  }
  /**
   * Thread safe addition of budget
   * @param newBudget budget to add
   */
  public synchronized void addBudget(float newBudget) {
    budget += newBudget;
  }
}
