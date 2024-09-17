package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.object.utilities;

import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.Tables;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

public interface ObjectUtilityService {
    PreparedStatement getPreparedStatementWithParameter(JSONObject jsonObject,
     String query, DataBase dataBase );
    String prepareInsertQuery(JSONObject jsonObject, Tables tableName);
    String prepareUpdateQuery(JSONObject jsonObject,Tables tableName);
    void closeConnection(ResultSet rs, Statement statement);
}
