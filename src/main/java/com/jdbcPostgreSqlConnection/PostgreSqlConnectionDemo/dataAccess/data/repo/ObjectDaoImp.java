package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.repo;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.casting.CastingService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection.ConnectionService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.utilities.ObjectUtility;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.utilities.ObjectUtilityService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.CrudWithParameters;
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
//    private final String PRIMARY_KEY=Tables.Orders.getPrimaryKey();
//    private final String TABLE_NAME= Tables.Orders.name();

    @Autowired
    public ObjectDaoImp(CastingService castingService, ConnectionService connectionService,
                        ObjectUtilityService objectUtilityService) {
        this.castingService = castingService;
        this.connectionService = connectionService;
        this.objectUtilityService = objectUtilityService;

    }

    @Override
    public Map<String,Object> getObjectById(Object id) {

        String getObjectByIdQuery = objectUtilityService.prepareGetObjectQuery();

        PreparedStatement preparedStatement = connectionService.getPreparedStatement
                (getObjectByIdQuery);
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
    public List<Map<String,Object>> getAllObjects() {
        String getAllObjectsQuery=objectUtilityService.prepareGetAllObjectsQuery();
        Statement statement = connectionService.getStatement(getAllObjectsQuery);
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(getAllObjectsQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Map<String,Object>> getObjectsList=castingService.resultSetToMapList(resultSet);
        objectUtilityService.closeConnection(resultSet,statement);
        return getObjectsList;
    }

    @Override
    public Object saveObject(JSONObject jsonObject)  {

        Object savedObjectId=null;
        ResultSet rs=null;
        PreparedStatement  preparedStatement=
                objectUtilityService.getPreparedStatementWithParameter
                        (jsonObject, CrudWithParameters.INSERT);
             int sayi=0;
        try {

            sayi=preparedStatement.executeUpdate();
            // Kaydedilen objenin ID sini aliyoruz burada.
            rs= preparedStatement.getGeneratedKeys();
            String primaryKey= ObjectUtility.PRIMARY_KEY;
            if (rs.next())
            {
                savedObjectId = rs.getInt(primaryKey);
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
    public int updateObject(JSONObject jsonObject)  {
//        String updateStringQuery=
//                objectUtilityService.prepareUpdateQuery(jsonObject);

        PreparedStatement preparedStatement =
                objectUtilityService.getPreparedStatementWithParameter
                        (jsonObject,CrudWithParameters.UPDATE);
        int rowsAffected;
        try {
            rowsAffected= preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        objectUtilityService.closeConnection(null,preparedStatement);
        return rowsAffected;
    }
    public int deleteObjectById(Object id)
    {
        String deleteObjectByIdQuery=
                objectUtilityService.prepareDeleteObjectQuery();

        PreparedStatement preparedStatement =
                connectionService.getPreparedStatement(deleteObjectByIdQuery);

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




