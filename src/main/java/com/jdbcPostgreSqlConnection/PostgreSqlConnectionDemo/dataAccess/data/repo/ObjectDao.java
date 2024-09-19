package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.repo;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.Tables;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ObjectDao {
    Map<String,Object> getObjectById(Object id);
    List<Map<String,Object>> getAllObjects();
    Object saveObject(JSONObject jsonObject) throws SQLException, ParseException;
    int updateObject(JSONObject jsonObject);
    int deleteObjectById(Object id);
}