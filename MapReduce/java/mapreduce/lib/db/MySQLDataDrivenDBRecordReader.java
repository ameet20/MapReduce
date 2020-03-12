package org.apache.hadoop.mapreduce.lib.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;

/**
 * A RecordReader that reads records from a MySQL table via DataDrivenDBRecordReader
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class MySQLDataDrivenDBRecordReader<T extends DBWritable>
    extends DataDrivenDBRecordReader<T> {

  public MySQLDataDrivenDBRecordReader(DBInputFormat.DBInputSplit split,
      Class<T> inputClass, Configuration conf, Connection conn, DBConfiguration dbConfig,
      String cond, String [] fields, String table) throws SQLException {
    super(split, inputClass, conf, conn, dbConfig, cond, fields, table, "MYSQL");
  }

  // Execute statements for mysql in unbuffered mode.
  protected ResultSet executeQuery(String query) throws SQLException {
    statement = getConnection().prepareStatement(query,
      ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    statement.setFetchSize(Integer.MIN_VALUE); // MySQL: read row-at-a-time.
    return statement.executeQuery();
  }
}
