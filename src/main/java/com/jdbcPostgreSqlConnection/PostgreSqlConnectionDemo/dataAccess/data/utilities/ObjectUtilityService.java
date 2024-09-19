package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.utilities;

import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.CrudWithParameters;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.Tables;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

public interface ObjectUtilityService {
    PreparedStatement getPreparedStatementWithParameter (JSONObject jsonObject, CrudWithParameters crudWithParameters);
    String prepareGetAllObjectsQuery();
    String prepareDeleteObjectQuery();
    String prepareGetObjectQuery();
    String prepareUpdateQuery(JSONObject jsonObject);
    String prepareInsertQuery(JSONObject jsonObject);
    void closeConnection(ResultSet rs, Statement statement);
}
