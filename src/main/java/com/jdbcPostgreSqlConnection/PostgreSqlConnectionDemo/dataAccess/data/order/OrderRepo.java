package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.order;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.DataBase;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.enums.Tables;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.object.ObjectDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepo implements OrderDao{

    private final DataBase dataBase=DataBase.POSTGRE_SQL;
    private final Tables table=Tables.Orders;

    @Autowired
    private ObjectDao objectDao;

    @Override
    public Map<String,Object> getOrderById(Object id) {
        return objectDao.getObjectById(id, table,dataBase);
    }
    @Override
    public  List<Map<String,Object>> getAllOrders() {
        StringBuilder getAllObjectsQuery=new StringBuilder("select ord.order_id, ");
        getAllObjectsQuery.append("ord.customer_id,");
        getAllObjectsQuery.append( "ord.employee_id,ord.shipped_date, " );
        getAllObjectsQuery.append("ord.freight,ord.ship_name,");
        getAllObjectsQuery.append("ord.ship_country ");
        getAllObjectsQuery.append("from orders as ord order by ord.order_id desc");
        return objectDao.getObjectsAsMap(getAllObjectsQuery.toString(),dataBase);
    }
    @Override
    public Object saveOrder(JSONObject ordr) {
        try {
          return  objectDao.saveObject(ordr, table,dataBase);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateOrder(JSONObject jsonObject) {
       return objectDao.updateObject(jsonObject, table,dataBase);
    }

    @Override
    public int deleteOrderById(int id) {
        return objectDao.deleteObjectById(id, table,dataBase);
    }
}
