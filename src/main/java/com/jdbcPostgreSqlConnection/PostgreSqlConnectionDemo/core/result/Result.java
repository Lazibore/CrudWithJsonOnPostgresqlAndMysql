package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result;

public class Result {
    private String message;
    private  boolean isSuccess;

    public Result(boolean isSuccess)
    {
        this.isSuccess=isSuccess;
    }

    public  Result(boolean isSuccess,String message)
    {
        this(isSuccess);
        this.message=message;
    }

    public String getMessage()
    {
        return this.message;
    }
    public boolean isSuccess()
    {
        return this.isSuccess;
    }


}
