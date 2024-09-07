package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.object;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.Tables;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ObjectDao {
    Map<String,Object> getObjectById(Object id, Tables tableName, DataBase dataBase);
    List<Map<String,Object>> getObjectsAsMap(String query, DataBase dataBase);
    Object saveObject(JSONObject jsonObject, Tables tableName, DataBase dataBase) throws SQLException, ParseException;
    int updateObject(JSONObject jsonObject,Tables tableName,DataBase dataBase );
    int deleteObjectById(Object id,Tables tableName,DataBase dataBase );
}