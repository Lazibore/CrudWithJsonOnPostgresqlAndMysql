package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;

import java.sql.PreparedStatement;
import java.sql.Statement;

public interface ConnectionService {
   PreparedStatement  getPreparedStatement(String query, DataBase dataBase);
   Statement  getStatement(String query,DataBase dataBase);
}
