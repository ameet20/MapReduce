package org.apache.hadoop.mapreduce.lib.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.conf.Configuration;

/**
 * A RecordReader that reads records from a Oracle table via DataDrivenDBRecordReader
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class OracleDataDrivenDBRecordReader<T extends DBWritable>
    extends DataDrivenDBRecordReader<T> {

  public OracleDataDrivenDBRecordReader(DBInputFormat.DBInputSplit split,
      Class<T> inputClass, Configuration conf, Connection conn,
      DBConfiguration dbConfig, String cond, String [] fields,
      String table) throws SQLException {

    super(split, inputClass, conf, conn, dbConfig, cond, fields, table,
        "ORACLE");

    // Must initialize the tz used by the connection for Oracle.
    OracleDBRecordReader.setSessionTimeZone(conf, conn);
  }
}
