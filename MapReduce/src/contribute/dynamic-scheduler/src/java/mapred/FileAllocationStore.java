package org.apache.hadoop.mapred;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Closeable;

/**
 * Implements persistent storage for queue budget and spending
 * information in a file.
 */
public class FileAllocationStore extends AllocationStore {
  private static final Log LOG = LogFactory.getLog(FileAllocationStore.class);
  private String fileName = "";
  private boolean loaded = false;

  /** {@inheritDoc} */
  public void init(Configuration conf) {
    fileName = conf.get(PrioritySchedulerOptions.DYNAMIC_SCHEDULER_BUDGET_FILE,
        "/etc/hadoop.budget");
  }

  /** {@inheritDoc} */
  public void save() {
    PrintWriter out = null; 
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
      for (BudgetQueue queue: getQueues()) {
        out.printf("%s %.20f %.20f\n", queue.name, queue.budget, 
            queue.spending);
      }
    } catch (Exception e) {
      LOG.error("Error writing to file: " + fileName, e);
    } finally {
      close(out);
    }
  }

  private void close(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception ce) {
        LOG.error("Error closing file: " + fileName, ce);
      }
    }
  }

  /** {@inheritDoc} */
  public void load() {
    if (loaded) {
      return;
    }
    BufferedReader in = null;
    try {
      in = new BufferedReader(new FileReader(fileName));
      String line = in.readLine();
      while (line != null) {
        String[] nameValue = line.split(" ");
        if (nameValue.length != 3) {
          continue;
        }
        queueCache.put(nameValue[0], new BudgetQueue(nameValue[0],
            Float.parseFloat(nameValue[1]), Float.parseFloat(nameValue[2])));
        line = in.readLine();
      } 
      loaded = true;
    } catch (Exception e) {
      LOG.error("Error reading file: " + fileName, e);
    } finally {
       close(in);
    }
  }
}
