package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.business.customer;

import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.DataResult;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.Result;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    DataResult<Map<String, Object>> getCustomerById(Object id);
    DataResult<List<Map<String,Object>>> getAllCustomers();
    DataResult<Object> saveCustomer(JSONObject order);
    Result updateCustomer(JSONObject order);
    Result deleteCustomerById(Object id);
}
