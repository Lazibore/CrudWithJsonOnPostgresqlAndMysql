package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.casting;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
@Component
public class CastingManager implements CastingService {

    public List<Map<String,Object>> resultSetToMapList(ResultSet rs) {
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
