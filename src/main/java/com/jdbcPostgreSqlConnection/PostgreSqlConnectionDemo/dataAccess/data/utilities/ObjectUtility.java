package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.utilities;

import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection.ConnectionService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.CrudWithParameters;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.Tables;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

@Repository
public class ObjectUtility implements ObjectUtilityService{
    public static final String PRIMARY_KEY= Tables.Orders.getPrimaryKey();
    private  final String TABLE_NAME= Tables.Orders.name();
    @Autowired
    private ConnectionService connectionService;

    // Parametreli olarak PreparedStatement Nesnesi donduruyoruz.
    public PreparedStatement getPreparedStatementWithParameter(JSONObject jsonObject, CrudWithParameters crudWithParameters)
    {
        String query="";
       switch (crudWithParameters)
       {
           case UPDATE -> query=this.prepareUpdateQuery(jsonObject);
           case INSERT -> query=this.prepareInsertQuery(jsonObject);
       }
        // Parametreleri olmaksizin query yi statement objesini olusturuyoruz.
        PreparedStatement preparedStatement = connectionService.getPreparedStatement(query);
        int index = 1;
        Iterator<String> keysIterator=jsonObject.keys();
        while (keysIterator.hasNext()) {
            String key = keysIterator.next();
            Object val = jsonObject.get(key);

            try {
                // Parametrelerin degerlerini set ediyoruz
                switch (val) {
                    case Integer i -> preparedStatement.setInt(index, i);
                    case Double v -> preparedStatement.setDouble(index, (double) val);
                    case Float v -> preparedStatement.setFloat(index, (float) val);
                    case Boolean b -> preparedStatement.setBoolean(index, (boolean) val);
                    case BigDecimal bigDecimal -> preparedStatement.setBigDecimal(index, bigDecimal);
                    case String string -> {
                        String dateString = val.toString().trim();
                        // Json ile gelen string ifadenin tarih mi olup olmadigini anliuoruz.
                        //Ona gore parametreyi set ediyorum.
                        try {
                            DateTimeFormatter originalFormatter = DateTimeFormatter
                                    .ofPattern("dd-MM-yyyy");
                            DateTimeFormatter newFormatter = DateTimeFormatter
                                    .ofPattern("yyyy-MM-dd");
                            LocalDate date = LocalDate.parse(dateString, originalFormatter);
                            String changedFormatWithDate = date.format(newFormatter);
                            Date dt = Date.valueOf(changedFormatWithDate);
                            preparedStatement.setDate(index, dt);
                        } catch (Exception e) {
                            preparedStatement.setString(index, dateString);
                        }
                    }
                    case null, default -> preparedStatement.setObject(index, val);
                }
            }

            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            index++;
        }
        return preparedStatement;
    }

    @Override
    public String prepareGetAllObjectsQuery() {
        StringBuffer getAllObjectsQuery=
                new StringBuffer("select * from ").append(TABLE_NAME);
        return getAllObjectsQuery.toString();
    }

    @Override
    public String prepareDeleteObjectQuery() {
        StringBuffer deleteObjectByIdQuery=
                new StringBuffer("delete from ");
        deleteObjectByIdQuery.append(TABLE_NAME).append(" where ").append(PRIMARY_KEY).append(" = ?");
        return deleteObjectByIdQuery.toString();
    }

    @Override
    public String prepareGetObjectQuery() {
        StringBuffer getObjectByIdQuery=
                new StringBuffer("select * from ");
        getObjectByIdQuery.append(TABLE_NAME).append( " where ").append(PRIMARY_KEY).append( "= ?");
        return getObjectByIdQuery.toString();
    }

    // Ekleme icin gerekli query i olusturuyorum.
    public String prepareInsertQuery(JSONObject jsonObject)
    {
        Iterator<String> keys = jsonObject.keys();
        StringBuffer insertQuery = new StringBuffer();
        StringBuffer columns = new StringBuffer();
        StringBuffer placeHolders = new StringBuffer();
        while (keys.hasNext()) {
            String next = keys.next();
            columns.append(next).append(",");
            placeHolders.append("?,");
        }
        columns.setLength((columns.length()) - 1);
        placeHolders.setLength((placeHolders.length()) - 1);

        insertQuery.append("insert into ").append(TABLE_NAME).append(" (")
                .append(columns).append(")").append(" VALUES(").append(placeHolders).append(")");

        return insertQuery.toString();
    }

    // Update icin gerekli query i olusturuyorum.
    public String prepareUpdateQuery(JSONObject jsonObject)
    {
        Object primaryKey=jsonObject.get(PRIMARY_KEY);
        // int yada String tipinde gelebilecek ID nin kontrolu icin cekiyorum.
        String primaryKeyType =primaryKey.getClass().getSimpleName();
        jsonObject.remove(PRIMARY_KEY);
        Iterator<String> keys= jsonObject.keys();

        StringBuilder keysQueryString=new StringBuilder();
        keysQueryString.append("update ").append(TABLE_NAME).append(" set ");
        while(keys.hasNext())
        {
            String key = keys.next();
            keysQueryString.append(key).append(" =?");
            if (keys.hasNext()) keysQueryString.append(",");
        }
        keysQueryString.append(" where ");
        keysQueryString.append(PRIMARY_KEY);
        keysQueryString.append(" = ");
        if (primaryKeyType.equals("Integer"))
        {
            keysQueryString.append(PRIMARY_KEY);
        }
        else
        {
            // String tipinde gelen ID yi veritabani 'Primary Key' seklinde anlayacagi icin yaptim.
            keysQueryString.append("'").append(PRIMARY_KEY).append("'");
        }

        return keysQueryString.toString();
    }


    // AutoCloseable olmasina ragmen acilan durum ve baglantilari kapatmak icin yazildi.
    public void closeConnection(ResultSet rs, Statement statement)
    {
        try {
            if (rs != null)
                if (!rs.isClosed()) {
                    rs.close();
                }
            if (statement != null)
            {
                if (statement.getConnection() != null)
                    statement.getConnection().close();

                if (!statement.isClosed())
                    statement.close();
            }
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
