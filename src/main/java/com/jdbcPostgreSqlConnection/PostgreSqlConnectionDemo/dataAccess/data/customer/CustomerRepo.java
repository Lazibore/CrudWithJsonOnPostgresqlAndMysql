package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.customer;
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
public class CustomerRepo implements CustomerDao{

    private final DataBase dataBase=DataBase.MYSQL;
    private final Tables table=Tables.Customers;

    @Autowired
    private ObjectDao objectDao;

    @Override
    public Map<String, Object> getCustomerById(Object id) {
        return objectDao.getObjectById(id,table,dataBase);

    }

    @Override
    public List<Map<String, Object>> getAllCustomers() {
        String getAllObjectsQuery="select * from Customers order by customer_id desc";
        return objectDao.getObjectsAsMap(getAllObjectsQuery,dataBase);
    }

    @Override
    public Object saveCustomer(JSONObject customer) {
        try {
            return  objectDao.saveObject(customer,table,dataBase);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateCustomer(JSONObject customer) {
        return objectDao.updateObject(customer,table,dataBase);
    }

    @Override
    public int deleteCustomerById(Object id) {
        return objectDao.deleteObjectById(id,table,dataBase);
    }
}
