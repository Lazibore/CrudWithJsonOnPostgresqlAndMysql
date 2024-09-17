package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class ConnectionManager implements ConnectionService,Runnable {
    private static volatile ConnectionManager connectionManager=null;
    private static volatile Connection conn=null;
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

    public static void setConn(DataBase dataBase)
    {
        try {       
            conn = DriverManager.getConnection(dataBase.getURL(),dataBase.getUSER_NAME(),dataBase.getPWD());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void setConnection(DataBase dataBase)
    {
        ConnectionManager.setInstance();
            synchronized (ConnectionManager.class) {
                if (conn == null) {
                    ConnectionManager.setConn(dataBase);
                }
                else
                {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ConnectionManager.setConn(dataBase);
                }
            }
    }

      @Override
    public PreparedStatement getPreparedStatement(String query,DataBase dataBase) {
        ConnectionManager.setConnection(dataBase);
        PreparedStatement preparedStatement =null;
        try {

            preparedStatement = conn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
            return preparedStatement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Statement getStatement(String query,DataBase dataBase) {
        ConnectionManager.setConnection(dataBase);
        Statement statement =null;
        try {
            statement = conn.createStatement();
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

    }
}
