package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.casting;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CastingService {
//    Map<String, Object> toMap(JSONObject object);
//    List<Object> toList(JSONArray array);
//    JSONArray getObjectsJsonArray(ResultSet resultSet);
     List resultSetToArrayList(ResultSet rs);
}
