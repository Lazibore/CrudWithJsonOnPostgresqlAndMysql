package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.casting;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface CastingService {
//    Map<String, Object> toMap(JSONObject object);
//    List<Object> toList(JSONArray array);
//    JSONArray getObjectsJsonArray(ResultSet resultSet);
     List<Map<String,Object>> resultSetToMapList(ResultSet rs);
}
