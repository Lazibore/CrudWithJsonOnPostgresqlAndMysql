package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public interface ConnectionService {
   PreparedStatement  getPreparedStatement(String query);
   Statement  getStatement(String query);
}
