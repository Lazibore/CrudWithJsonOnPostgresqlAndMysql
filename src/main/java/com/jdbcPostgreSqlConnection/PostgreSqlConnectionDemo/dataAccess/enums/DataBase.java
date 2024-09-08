package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums;

public enum DataBase {
    POSTGRE_SQL("jdbc:postgresql://localhost:5432/",
            "postgres", "*", "Northwind"),

    MYSQL("jdbc:mysql://localhost:3306/",
            "root", "*", "northwind");

    private final String URL;
    private final String USER_NAME;
    private final String PWD;
    private final String DATABASE_NAME;
    DataBase(String URL,String USER_NAME,String PWD,String DATABASE_NAME)
    {
        this.URL=URL;
        this.USER_NAME=USER_NAME;
        this.PWD=PWD;
        this.DATABASE_NAME=DATABASE_NAME;
    }

    public String getURL() {
        StringBuilder url=new StringBuilder(this.URL);
        url.append(this.DATABASE_NAME);
        return url.toString();
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getPWD() {
        return PWD;
    }

    public String getDATABASE_NAME() {
        return DATABASE_NAME;
    }
}
