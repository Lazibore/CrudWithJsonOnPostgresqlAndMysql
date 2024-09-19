package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class ConnectionManager implements ConnectionService {
    private static volatile ConnectionManager connectionManager=null;
    private static volatile Connection conn=null;
    private static final DataBase dataBase=DataBase.POSTGRE_SQL;
    private ConnectionManager()
    {

    }
    public static void setInstance()
    {
            synchronized (ConnectionManager.class) {
                if (connectionManager == null) {
                    connectionManager = new ConnectionManager();
                }

            }
    }

    public static void setConn()
    {

        try {       
            conn = DriverManager.getConnection(dataBase.getURL(),dataBase.getUSER_NAME(),dataBase.getPWD());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void setConnection()
    {
        ConnectionManager.setInstance();
            synchronized (ConnectionManager.class) {
                if (conn == null) {
                    ConnectionManager.setConn();
                }
                else
                {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ConnectionManager.setConn();
                }
            }
    }

      @Override
    public PreparedStatement getPreparedStatement(String query) {
        ConnectionManager.setConnection();
        PreparedStatement preparedStatement =null;
        try {

            preparedStatement = conn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Statement getStatement(String query) {
        ConnectionManager.setConnection();
        Statement statement =null;
        try {
            statement = conn.createStatement();
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
