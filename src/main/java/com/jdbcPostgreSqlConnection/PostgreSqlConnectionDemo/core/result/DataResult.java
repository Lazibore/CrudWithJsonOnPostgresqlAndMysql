package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result;

import org.json.JSONArray;

public class DataResult<T> extends Result{
    private T data;

    public DataResult(T data,boolean isSuccess) {
        super(isSuccess);
        this.data=data;
    }

    public DataResult(T data,boolean isSuccess,String message) {
        super(isSuccess,message);
        this.data=data;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data=data;
    }
}
