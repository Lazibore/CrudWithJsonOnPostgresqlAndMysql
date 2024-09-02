package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.list;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.Tables;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ObjectDao {
    Map<String,Object> getObjectById(Object id, Tables tableName);
    List<Map<String,Object>> getObjectsAsMap(String query);
    Object saveObject(JSONObject jsonObject, Tables tableName) throws SQLException, ParseException;
    int updateObject(JSONObject jsonObject,Tables tableName);
    int deleteObjectById(Object id,Tables tableName);
}