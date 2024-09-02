package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.customer;

import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.enums.Tables;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.object.ObjectDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class CustomerRepo implements CustomerDao{
    @Autowired
    private ObjectDao objectDao;

    @Override
    public Map<String, Object> getCustomerById(Object id) {
        return objectDao.getObjectById(id,Tables.Customers);
    }

    @Override
    public List<Map<String, Object>> getAllCustomers() {
        String getAllObjectsQuery="select * from Customers order by customer_id desc";
        return objectDao.getObjectsAsMap(getAllObjectsQuery);
    }

    @Override
    public Object saveCustomer(JSONObject customer) {
        try {
            return  objectDao.saveObject(customer, Tables.Customers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateCustomer(JSONObject customer) {
        return objectDao.updateObject(customer,Tables.Customers);
    }

    @Override
    public int deleteCustomerById(Object id) {
        return objectDao.deleteObjectById(id,Tables.Customers);
    }
}
