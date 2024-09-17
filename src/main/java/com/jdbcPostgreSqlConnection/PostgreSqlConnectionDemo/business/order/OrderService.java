package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.business.order;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.DataResult;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.Result;
import org.json.JSONObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public interface OrderService{
    Future<DataResult<Map<String, Object>>>  getOrderById(Object id);
    Future<DataResult< List<Map<String,Object>>>> getAllOrders();
    Future<DataResult<Object>> saveOrder(JSONObject order);
    Future<Result>  updateOrder(JSONObject order);
    Future<Result> deleteOrderById(int id);
}
