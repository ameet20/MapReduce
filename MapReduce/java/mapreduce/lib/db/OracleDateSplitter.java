package org.apache.hadoop.mapreduce.lib.db;

import java.util.Date;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Implement DBSplitter over date/time values returned by an Oracle db.
 * Make use of logic from DateSplitter, since this just needs to use
 * some Oracle-specific functions on the formatting end when generating
 * InputSplits.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class OracleDateSplitter extends DateSplitter {

  @SuppressWarnings("unchecked")
  @Override
  protected String dateToString(Date d) {
    // Oracle Data objects are always actually Timestamps
    return "TO_TIMESTAMP('" + d.toString() + "', 'YYYY-MM-DD HH24:MI:SS.FF')";
  }
}
