package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.entity.Order;
import org.json.JSONArray;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public interface ConnectionService {
   PreparedStatement  getPreparedStatement(String query);
   Statement  getStatement(String query);
}
