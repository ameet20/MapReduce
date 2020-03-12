package org.apache.hadoop.mapreduce.lib.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.InputSplit;

/**
 * Implement DBSplitter over boolean values.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class BooleanSplitter implements DBSplitter {
  public List<InputSplit> split(Configuration conf, ResultSet results, String colName)
      throws SQLException {

    List<InputSplit> splits = new ArrayList<InputSplit>();

    if (results.getString(1) == null && results.getString(2) == null) {
      // Range is null to null. Return a null split accordingly.
      splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(
          colName + " IS NULL", colName + " IS NULL"));
      return splits;
    }

    boolean minVal = results.getBoolean(1);
    boolean maxVal = results.getBoolean(2);

    // Use one or two splits.
    if (!minVal) {
      splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(
          colName + " = FALSE", colName + " = FALSE"));
    }

    if (maxVal) {
      splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(
          colName + " = TRUE", colName + " = TRUE"));
    }

    if (results.getString(1) == null || results.getString(2) == null) {
      // Include a null value.
      splits.add(new DataDrivenDBInputFormat.DataDrivenDBInputSplit(
          colName + " IS NULL", colName + " IS NULL"));
    }

    return splits;
  }
}
