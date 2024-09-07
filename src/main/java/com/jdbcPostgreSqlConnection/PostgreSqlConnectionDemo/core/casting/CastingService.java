package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.casting;

import java.sql.ResultSet;
import java.util.List;

public interface CastingService {
//    Map<String, Object> toMap(JSONObject object);
//    List<Object> toList(JSONArray array);
//    JSONArray getObjectsJsonArray(ResultSet resultSet);
     List resultSetToArrayList(ResultSet rs);
}
