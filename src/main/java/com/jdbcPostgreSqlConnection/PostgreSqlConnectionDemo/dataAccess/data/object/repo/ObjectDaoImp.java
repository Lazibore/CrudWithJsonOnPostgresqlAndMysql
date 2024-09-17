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
    private ObjectUtilityService objectUtilityService;

    @Autowired
    public ObjectDaoImp(CastingService castingService, ConnectionService connectionService,
                        ObjectUtilityService objectUtilityService) {
        this.castingService = castingService;
        this.connectionService = connectionService;
        this.objectUtilityService = objectUtilityService;
    }



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

        String query=  objectUtilityService.prepareInsertQuery(jsonObject,tableName);
        Object savedObjectId=null;
        PreparedStatement  preparedStatement=
                objectUtilityService.getPreparedStatementWithParameter(jsonObject,jsonObject.keys(),query,dataBase);
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

            objectUtilityService.closeConnection(rs,preparedStatement);
            return savedObjectId;
        }
        catch (SQLException e) {
            return sayi;
        }

    }

    @Override
    public int updateObject(JSONObject jsonObject,Tables tableName,DataBase dataBase )  {
        String updateStringQuery= objectUtilityService.prepareUpdateQuery(jsonObject,tableName);
        PreparedStatement preparedStatement =
                objectUtilityService.getPreparedStatementWithParameter(jsonObject,jsonObject.keys(),updateStringQuery,dataBase);
        int rowsAffected;
        try {
            rowsAffected= preparedStatement.executeUpdate();
            objectUtilityService.closeConnection(null,preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsAffected;
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




