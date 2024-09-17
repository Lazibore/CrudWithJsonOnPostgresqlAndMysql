package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.object.repo;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.casting.CastingService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection.ConnectionService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.object.utilities.ObjectUtilityService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.Tables;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.List;
import java.util.Map;

@Repository
public class ObjectDaoImp implements ObjectDao {

    private final CastingService castingService;
    private final ConnectionService connectionService;
    private final ObjectUtilityService objectUtilityService;

    @Autowired
    public ObjectDaoImp(CastingService castingService, ConnectionService connectionService,
                        ObjectUtilityService objectUtilityService) {
        this.castingService = castingService;
        this.connectionService = connectionService;
        this.objectUtilityService = objectUtilityService;
    }

    @Override
    public Map<String,Object> getObjectById(Object id, Tables tableName, DataBase dataBase) {

        String getObjectByIdQuery = "select * from " +
                tableName.name()
                +
                " where "
                + tableName.getPrimaryKey() + "= ?";

        PreparedStatement preparedStatement = connectionService.getPreparedStatement
                (getObjectByIdQuery, dataBase);
        ResultSet resultSet;
        List<Map<String, Object>> objectMapList = null;
        try {
            try {
                int idInt = Integer.parseInt(id.toString());
                preparedStatement.setInt(1, idInt);
            } catch (NumberFormatException b) {
                preparedStatement.setString(1, id.toString());
            }
            resultSet = preparedStatement.executeQuery();

            objectMapList = castingService.resultSetToMapList(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        objectUtilityService.closeConnection(resultSet,preparedStatement);
        return objectMapList.isEmpty() ? null : objectMapList.getFirst();
    }
    @Override
    public List<Map<String,Object>> getObjectsAsMap(String query, DataBase dataBase) {
        Statement statement = connectionService.getStatement(query,dataBase);
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Map<String,Object>> getObjectsList=castingService.resultSetToMapList(resultSet);
        objectUtilityService.closeConnection(resultSet,statement);
        return getObjectsList;
    }

    @Override
    public Object saveObject(JSONObject jsonObject, Tables tableName,DataBase dataBase )  {

        String query=  objectUtilityService.prepareInsertQuery(jsonObject,tableName);
        Object savedObjectId=null;
        ResultSet rs=null;
        PreparedStatement  preparedStatement=
                objectUtilityService.getPreparedStatementWithParameter
                        (jsonObject,query,dataBase);
             int sayi=0;
        try {
            String primaryKey=tableName.getPrimaryKey();
            sayi=preparedStatement.executeUpdate();
            // Kaydedilen objenin ID sini aliyoruz burada.
            rs= preparedStatement.getGeneratedKeys();

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
            return savedObjectId == null ? sayi : savedObjectId;
        }
        catch (SQLException e) {
            return sayi;
        }
        finally {
            objectUtilityService.closeConnection(rs,preparedStatement);
        }
    }

    @Override
    public int updateObject(JSONObject jsonObject,Tables tableName,DataBase dataBase )  {
        String updateStringQuery= objectUtilityService.prepareUpdateQuery(jsonObject,tableName);
        PreparedStatement preparedStatement =
                objectUtilityService.getPreparedStatementWithParameter
                        (jsonObject,updateStringQuery,dataBase);
        int rowsAffected;
        try {
            rowsAffected= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        objectUtilityService.closeConnection(null,preparedStatement);
        return rowsAffected;
    }
    public int deleteObjectById(Object id,Tables tableName,DataBase dataBase)
    {
        String deleteObjectByIdQuery="delete from " +tableName.name()
                +" where "+tableName.getPrimaryKey()+ " = ?";

        PreparedStatement preparedStatement =
                connectionService.getPreparedStatement(deleteObjectByIdQuery,dataBase);

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
        objectUtilityService.closeConnection(null,preparedStatement);
        return rowsAffected;
    }

}




