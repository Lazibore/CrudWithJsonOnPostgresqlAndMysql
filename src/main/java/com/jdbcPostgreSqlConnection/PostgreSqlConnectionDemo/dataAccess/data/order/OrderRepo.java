package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.order;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.Tables;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.list.ObjectDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class OrderRepo implements OrderDao{

    @Autowired
    private ObjectDao objectDao;

    @Override
    public Map<String,Object> getOrderById(Object id) {
        return objectDao.getObjectById(id, Tables.Orders);
    }

    @Override
    public  List<Map<String,Object>> getAllOrders() {
        String getAllObjectsQuery=
                "select ord.order_id, " +
                "ord.customer_id," +
                "ord.employee_id,ord.shipped_date, " +
                "ord.freight,ord.ship_name," +
                "ord.ship_country " +
                "from orders as ord order by ord.order_id desc";
        return objectDao.getObjectsAsMap(getAllObjectsQuery);
    }

    @Override
    public Object saveOrder(JSONObject ordr) {
        try {
          return  objectDao.saveObject(ordr, Tables.Orders);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateOrder(JSONObject jsonObject) {
       return objectDao.updateObject(jsonObject, Tables.Orders);
    }

    @Override
    public int deleteOrderById(int id) {
        return objectDao.deleteObjectById(id, Tables.Orders);
    }
}
