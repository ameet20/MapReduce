package org.apache.hadoop.mapreduce.lib.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.MRJobConfig;

/**
 * Implement DBSplitter over floating-point values.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class FloatSplitter implements DBSplitter {

  private static final Log LOG = LogFactory.getLog(FloatSplitter.class);

  private static final double MIN_INCREMENT = 10000 * Double.MIN_VALUE;

  public List<InputSplit> split(Configuration conf, ResultSet results, String colName)
      throws SQLException {

    LOG.warn("Generating splits for a floating-point index column. Due to the");
    LOG.warn("imprecise representation of floating-point values in Java, this");
    LOG.warn("may result in an incomplete import.");
    LOG.warn("You are strongly encouraged to choose an integral split column.");

    List<InputSplit> splits = new ArrayList<InputSplit>();

    if (results.getString(1) == null && results.getString(2) == null) {
      // Range is null to null. Return a null split accordingly.
      splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(
          colName + " IS NULL", colName + " IS NULL"));
      return splits;
    }

    double minVal = results.getDouble(1);
    double maxVal = results.getDouble(2);

    // Use this as a hint. May need an extra task if the size doesn't
    // divide cleanly.
    int numSplits = conf.getInt(MRJobConfig.NUM_MAPS, 1);
    double splitSize = (maxVal - minVal) / (double) numSplits;

    if (splitSize < MIN_INCREMENT) {
      splitSize = MIN_INCREMENT;
    }

    String lowClausePrefix = colName + " >= ";
    String highClausePrefix = colName + " < ";

    double curLower = minVal;
    double curUpper = curLower + splitSize;

    while (curUpper < maxVal) {
      splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(
          lowClausePrefix + Double.toString(curLower),
          highClausePrefix + Double.toString(curUpper)));

      curLower = curUpper;
      curUpper += splitSize;
    }

    // Catch any overage and create the closed interval for the last split.
    if (curLower <= maxVal || splits.size() == 1) {
      splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(
          lowClausePrefix + Double.toString(curUpper),
          colName + " <= " + Double.toString(maxVal)));
    }

    if (results.getString(1) == null || results.getString(2) == null) {
      // At least one extrema is null; add a null split.
      splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(
          colName + " IS NULL", colName + " IS NULL"));
    }

    return splits;
  }
}
