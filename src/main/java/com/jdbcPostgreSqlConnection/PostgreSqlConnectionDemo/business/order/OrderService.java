package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.business.order;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.DataResult;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.Result;
import org.json.JSONObject;
import java.util.List;
import java.util.Map;

public interface OrderService{
    DataResult<Map<String, Object>> getOrderById(Object id);
    DataResult< List<Map<String,Object>>> getAllOrders();
    DataResult<Object> saveOrder(JSONObject order);
    Result updateOrder(JSONObject order);
    Result deleteOrderById(int id);
}
