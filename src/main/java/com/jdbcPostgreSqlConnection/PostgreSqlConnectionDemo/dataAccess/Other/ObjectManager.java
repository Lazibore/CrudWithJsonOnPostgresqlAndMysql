//package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.Other;
//
//import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.utilities.PrimaryKeyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ObjectManager<T> implements ObjectDao<T> {
//
////    private   CastingService castingService;
//    private PrimaryKeyService primaryKeyService;
//
//    @Autowired
//    public ObjectManager(PrimaryKeyService primaryKeyService) {
//
//        this.primaryKeyService = primaryKeyService;
//    }
//
//    public static <T> String getClassName(Class<T> clazz) {
//        return clazz.getName();
//    }
//
//    @Override
//    public T getObjectById(int id) {
//        String primaryKey =primaryKeyService.getPrimaryKeyFieldName();
//        String query = "select * from " + String.class + "where "+ primaryKey + "="+id;
//       T t = (T) getObjectAll(query).getFirst();
//        return t;
//    }
//
//
//
//    @Override
//    public List getObjectAll(String query) {
//        return List.of();
//    }
//
////    @Override
////    public void deletObjectById(String id) {
////
////    }
////
////    @Override
////    public void insertObject(Object object) {
////
////    }
//}
