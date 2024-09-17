package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums;

public enum Tables {
    Orders("order_id"),
    Customers("customer_id"),
    Employees("employee_id");

    private final String primaryKey;

    Tables(String primaryKey)
    {
        this.primaryKey=primaryKey;
    }
public String getPrimaryKey(){
    return this.primaryKey;
}
}
