package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.business.customer;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.*;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.customer.CustomerDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class CustomerManager implements CustomerService{
    @Autowired
    private CustomerDao customerDao;
    @Override
    public DataResult<Map<String, Object>> getCustomerById(Object id) {
        Map<String, Object> customer=customerDao.getCustomerById(id);
        if(customer==null)
            return new ErrorDataResult<>("Customer not found");

        return new SuccessDataResult<>(customer,"Customer found");
    }

    @Override
    public DataResult<List<Map<String, Object>>> getAllCustomers() {
        List<Map<String,Object>> customers=customerDao.getAllCustomers();
        if (customers==null)
                   return new ErrorDataResult<>("Customer not found");
               else
            return new SuccessDataResult<>(customers,"Customer listed");
    }

    @Override
    public DataResult<Object> saveCustomer(JSONObject customer) {
        Object savedCustomerId =customerDao.saveCustomer(customer);

        if (savedCustomerId.equals(0))
            return new ErrorDataResult<>("Not saved");
        else
            return new SuccessDataResult<>(savedCustomerId,"Saved successfully");
    }

    @Override
    public Result updateCustomer(JSONObject customer) {
        int rowsAffected=customerDao.updateCustomer(customer);
        String message;
        if (rowsAffected ==0)
        {
            message="Not updated";
            return new ErrorResult(message);
        }
        else
        {
            message="Updated successfuly";
            return new SuccesResult(message);
        }
    }

    @Override
    public Result deleteCustomerById(Object id) {
        int rowsAffected=customerDao.deleteCustomerById(id);
        String message;
        if (rowsAffected ==0)
        {
            message="Not deleted";
            return new ErrorResult(message);
        }
        else
        {
            message="Customer deleted";
            return new SuccesResult(message);
        }
    }
}
