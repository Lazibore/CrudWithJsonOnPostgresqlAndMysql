package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.object.utilities;

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

@Repository
public class ObjectUtility implements ObjectUtilityService{

    @Autowired
    private ConnectionService connectionService;

    // Parametreli olarak PreparedStatement Nesnesi donduruyoruz.
    public PreparedStatement getPreparedStatementWithParameter(JSONObject jsonObject,
     String query, DataBase dataBase )
    {
        // Parametreleri olmaksizin query yi statement objesini olusturuyoruz.
        PreparedStatement preparedStatement = connectionService.getPreparedStatement(query,dataBase);
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

    // Ekleme icin gerekli query i olusturuyorum.
    public String prepareInsertQuery(JSONObject jsonObject, Tables tableName)
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

    // Update icin gerekli query i olusturuyorum.
    public String prepareUpdateQuery(JSONObject jsonObject,Tables tableName)
    {
        Object primaryKey=jsonObject.get(tableName.getPrimaryKey());
        // int yada String tipinde gelebilecek ID nin kontrolu icin cekiyorum.
        String primaryKeyType =primaryKey.getClass().getSimpleName();
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
        if (primaryKeyType.equals("Integer"))
        {
            keysQueryString.append(primaryKey);
        }
        else
        {
            // String tipinde gelen ID yi veritabani 'Primary Key' seklinde anlayacagi icin yaptim.
            keysQueryString.append("'").append(primaryKey).append("'");
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
