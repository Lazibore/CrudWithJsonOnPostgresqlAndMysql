package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.customer;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface CustomerDao {
    Map<String,Object> getCustomerById(Object id);
    List<Map<String,Object>> getAllCustomers();
    Object saveCustomer(JSONObject order);
    int updateCustomer(JSONObject jsonObject);
    int deleteCustomerById(Object id);
}
