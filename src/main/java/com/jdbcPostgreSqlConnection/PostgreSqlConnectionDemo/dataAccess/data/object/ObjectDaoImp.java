package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.object;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.casting.CastingService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection.ConnectionService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.Tables;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class ObjectDaoImp implements ObjectDao {

    private CastingService castingService;
    private ConnectionService connectionService;

    @Autowired
    public ObjectDaoImp(CastingService castingService, ConnectionService connectionService) {
        this.castingService = castingService;
        this.connectionService = connectionService;
    }

    @Override
    public Map<String,Object> getObjectById(Object id, Tables tableName, DataBase dataBase) {
        String getObjectByIdQuery="select * from " + tableName.name() + " where " +tableName.getPrimaryKey() + "= ?";
        PreparedStatement preparedStatement = connectionService.getPreparedStatement(getObjectByIdQuery,dataBase);
        ResultSet resultSet;
        Map<String,Object> object=null;

        try {
                try
                {
                   int idInt= Integer.parseInt(id.toString());
                    preparedStatement.setInt(1, idInt);
                }
                catch( NumberFormatException b )
                {
                    preparedStatement.setString(1,  id.toString());
                }
                 resultSet = preparedStatement.executeQuery();
                List<Map<String,Object>> objectMapList=castingService.resultSetToArrayList(resultSet);
                object=objectMapList.get(0);

        } catch (SQLException e) {
            object=null;
        }
        finally {
            return object;
        }

    }

    @Override
    public List getObjectsAsMap(String query, DataBase dataBase) {
        Statement statement = connectionService.getStatement(query,dataBase);
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return castingService.resultSetToArrayList(resultSet);
    }

    @Override
    public Object saveObject(JSONObject jsonObject, Tables tableName,DataBase dataBase )  {

        String query=  this.prepareInsertQuery(jsonObject,tableName);
        Object savedObjectId=null;
        PreparedStatement  preparedStatement=
            getPreparedStatementWithParameter(jsonObject,jsonObject.keys(),query,dataBase);
             int sayi=0;
        try {
            String primaryKey=tableName.getPrimaryKey();
             sayi=preparedStatement.executeUpdate();
            ResultSet rs= preparedStatement.getGeneratedKeys();

            if (rs.next())
            {
                try {
                    savedObjectId = rs.getInt(primaryKey);
                    }
                catch (SQLException  e)
                {
                    savedObjectId = rs.getString(primaryKey);
                }
            }
            if(!rs.isClosed()) rs.close();
            if (!preparedStatement.isClosed()) preparedStatement.close();
            if (preparedStatement.getConnection() != null) preparedStatement.getConnection().close();
            return savedObjectId;
        }
        catch (SQLException e) {
            return sayi;
        }

    }


    @Override
    public int updateObject(JSONObject jsonObject,Tables tableName,DataBase dataBase )  {
        String updateStringQuery= prepareUpdateQuery(jsonObject,tableName);
        PreparedStatement preparedStatement =
                getPreparedStatementWithParameter(jsonObject,jsonObject.keys(),updateStringQuery,dataBase);
        int rowsAffected;
        try {
            rowsAffected= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsAffected;
    }


    private String prepareUpdateQuery(JSONObject jsonObject,Tables tableName)
    {
        Object primaryKey=jsonObject.get(tableName.getPrimaryKey());
        String primaryKeyType =primaryKey.getClass().getTypeName();
        jsonObject.remove(tableName.getPrimaryKey());
        Iterator<String> keys= jsonObject.keys();

        StringBuilder keysQueryString=new StringBuilder();
        keysQueryString.append("update ").append(tableName).append(" set ");
        while(keys.hasNext())
        {
            String key = keys.next();
            keysQueryString.append(key).append(" =?");
            if (keys.hasNext()) keysQueryString.append(",");
        }
        keysQueryString.append(" where ");
        keysQueryString.append(tableName.getPrimaryKey());
        keysQueryString.append(" = ");
        if (primaryKeyType.equals("java.lang.Integer"))
        {
            keysQueryString.append(primaryKey);
        }
        else
        {
            keysQueryString.append("'").append(primaryKey).append("'");
        }

        return keysQueryString.toString();
    }

        private String prepareInsertQuery(JSONObject jsonObject,Tables tableName)
        {
            Iterator<String> keys = jsonObject.keys();
            StringBuilder insertQuery = new StringBuilder();
            StringBuilder columns = new StringBuilder();
            StringBuilder placeHolders = new StringBuilder();
            while (keys.hasNext()) {
                String next = keys.next();
                columns.append(next).append(",");
                placeHolders.append("?,");
            }
            columns.setLength((columns.length()) - 1);
            placeHolders.setLength((placeHolders.length()) - 1);

            insertQuery.append("insert into ").append(tableName.name()).append(" (")
            .append(columns).append(")").append(" VALUES(").append(placeHolders).append(")");

            return insertQuery.toString();
        }
    private PreparedStatement getPreparedStatementWithParameter(JSONObject jsonObject,
      Iterator<String> keysIterator, String query,DataBase dataBase )
    {
        PreparedStatement preparedStatement = connectionService.getPreparedStatement(query,dataBase);
        int index = 1;

        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            Object val = jsonObject.get(key);

            try {
                if (val instanceof Integer) {
                    preparedStatement.setInt(index, (Integer) val);
                } else if (val instanceof Double) {
                    preparedStatement.setDouble(index, (double) val);
                } else if (val instanceof Float) {
                    preparedStatement.setFloat(index, (float) val);
                } else if (val instanceof Boolean) {
                    preparedStatement.setBoolean(index, (boolean) val);
                } else if (val instanceof BigDecimal) {
                    preparedStatement.setBigDecimal(index, (BigDecimal) val);
                } else if (val instanceof String) {
                    String dateString=val.toString().trim();
                    try {
                        DateTimeFormatter originalFormatter = DateTimeFormatter
                                .ofPattern("dd-MM-yyyy");
                        DateTimeFormatter newFormatter = DateTimeFormatter
                                .ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(dateString, originalFormatter);
                        String changedFormatWithDate=date.format(newFormatter);
                        Date dt=Date.valueOf(changedFormatWithDate);
                        preparedStatement.setDate(index, dt);
                    }
                    catch (Exception e)
                    {
                        preparedStatement.setString(index, dateString);
                    }
                }
                else
                    preparedStatement.setObject(index, val);
            }

            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            index++;
        }
        return preparedStatement;
    }

    public int deleteObjectById(Object id,Tables tableName,DataBase dataBase)
    {
        String deleteObjectByIdQuery="delete from " +tableName.name() +" where "+tableName.getPrimaryKey()+ " = ?";
        PreparedStatement preparedStatement = connectionService.getPreparedStatement(deleteObjectByIdQuery,dataBase);
        int rowsAffected;
        try {
            preparedStatement.setInt(1, (int) id);
            rowsAffected = preparedStatement.executeUpdate();
        }
        catch (SQLException | ClassCastException e) {
            try {
                    preparedStatement.setString(1,  id.toString());
                    rowsAffected = preparedStatement.executeUpdate();
                }
            catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
        }
        return rowsAffected;
    }

}




