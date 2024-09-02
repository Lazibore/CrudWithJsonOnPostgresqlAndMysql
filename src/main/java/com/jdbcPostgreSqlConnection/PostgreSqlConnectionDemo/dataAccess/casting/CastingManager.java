package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.casting;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
//import java.util.stream.IntStream;
@Service
public class CastingManager implements CastingService {

//    public JSONArray getObjectsJsonArray(ResultSet resultSet) {
//        JSONArray jsonArray = new JSONArray();
//        try {
//            ResultSetMetaData md = resultSet.getMetaData();
//            int numCols = md.getColumnCount();
//            List<String> colNames = IntStream.range(0, numCols)
//                    .mapToObj(i -> {
//                        try {
//                            return md.getColumnName(i + 1);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                            return "?";
//                        }
//                    })
//                    .toList();
//
//            while (resultSet.next()) {
//                JSONObject row = new JSONObject();
//                colNames.forEach(cn -> {
//                    try {
//                        row.put(cn, resultSet.getObject(cn));
//                    } catch (JSONException | SQLException e) {
//                        e.printStackTrace();
//                    }
//                });
//                jsonArray.put(row);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return jsonArray;
//    }

//    public JSONArray getObjectList(ResultSet resultSet) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        List Tlist= null;
//        try {
//            Tlist = objectMapper.readValue(getObjectJsonArray(resultSet).toString(), new TypeReference<List<T>>(){});
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return Tlist;
//    }
//    public Map<String, Object> toMap(JSONObject object) throws JSONException {
//        Map<String, Object> map = new HashMap<String, Object>();
//        Iterator<String> keysItr = object.keys();
//        while(keysItr.hasNext()) {
//            String key = keysItr.next();
//            Object value = object.get(key);
//
//            if(value instanceof JSONArray) {
//                value = toList((JSONArray) value);
//            }
//            else if(value instanceof JSONObject) {
//                value = toMap((JSONObject) value);
//            }
//            map.put(key, value);
//        }
//        return map;
//    }
//    public List<Object> toList(JSONArray array) throws JSONException {
//        List<Object> list = new ArrayList<>();
//        for(int i = 0; i < array.length(); i++) {
//            Object value = array.get(i);
//            if(value instanceof JSONArray) {
//                value = toList((JSONArray) value);
//            }
//            else if(value instanceof JSONObject) {
//                value = toMap((JSONObject) value);
//            }
//            list.add(value);
//        }
//        return list;
//    }

    public List<Map<String,Object>> resultSetToArrayList(ResultSet rs) {

        ResultSetMetaData md ;
        List<Map<String,Object>> list;
        try {
            md = rs.getMetaData();
        int columns = md.getColumnCount();
         list = new ArrayList<>();
                while(rs.next())
                {
                   Map<String,Object> row = new HashMap<>(columns);
                    for(int i=1; i<=columns; ++i){
                        row.put(md.getColumnName(i),rs.getObject(i));
                    }
                    list.add(row);
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }





}
