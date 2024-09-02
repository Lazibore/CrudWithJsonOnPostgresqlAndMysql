package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.order;
import org.json.JSONObject;
import java.util.List;
import java.util.Map;
public interface OrderDao  {
    Map<String,Object> getOrderById(Object id);
    List<Map<String,Object>> getAllOrders();
    Object saveOrder(JSONObject order);
    int updateOrder(JSONObject jsonObject);
    int deleteOrderById(int id);
}
