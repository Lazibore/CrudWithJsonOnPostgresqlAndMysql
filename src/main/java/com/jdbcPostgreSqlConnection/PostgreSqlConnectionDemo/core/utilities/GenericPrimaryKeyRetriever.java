package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.utilities;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
@Service
public class GenericPrimaryKeyRetriever<T> implements PrimaryKeyService {

    private  Class<T> type;

    public GenericPrimaryKeyRetriever() {
    }


    public GenericPrimaryKeyRetriever(Class<T> type) {
        this.type = type;
    }


    public String getPrimaryKeyFieldName() {
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                return field.getName();
            }
        }
        throw new RuntimeException("No primary key field found in class: " + type.getName());
    }




}
