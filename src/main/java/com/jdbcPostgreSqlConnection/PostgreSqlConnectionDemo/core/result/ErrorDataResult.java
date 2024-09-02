package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result;

public class ErrorDataResult<T>  extends DataResult<T>{

    public ErrorDataResult() {
        super(null,false);
    }
    public ErrorDataResult(T data) {
        super(data,false);
    }
    public ErrorDataResult(String message) {
        super(null,false,message);
    }

    public ErrorDataResult(String message, T data) {
        super(data,false,message);
    }


}
