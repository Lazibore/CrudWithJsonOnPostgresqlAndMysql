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
        Map<String, Object> islem=customerDao.getCustomerById(id);
        if(islem==null)
        {
            return new ErrorDataResult<>("Musteri Bulunamadi");
        }
        return new SuccessDataResult<>(islem,"Islem basarili");
    }

    @Override
    public DataResult<List<Map<String, Object>>> getAllCustomers() {
        List<Map<String,Object>> customers=customerDao.getAllCustomers();
        if (customers==null)
        {
            return new ErrorDataResult<>("Musteri bulunamadi.");
        }
        else
        {
            return new SuccessDataResult<>(customers,"Islem basarili");
        }
    }

    @Override
    public DataResult<Object> saveCustomer(JSONObject customer) {
        Object savedCustomerId =customerDao.saveCustomer(customer);
        if (savedCustomerId.equals(0))
        {
            return new ErrorDataResult<>("Kaydetme islemi basarisiz");
        }
        else if(savedCustomerId.equals(1))
        {
            return new SuccessDataResult<>("Kaydetme islemi yapildi fakat bir terslik var.");
        }
        else
        {
            return new SuccessDataResult<>(savedCustomerId,"Kaydetme islemi basarili");
        }
    }

    @Override
    public Result updateCustomer(JSONObject customer) {
        int rowsAffected=customerDao.updateCustomer(customer);
        String message;
        if (rowsAffected ==0)
        {
            message="Guncelleme islemi basarisiz.";
            return new ErrorResult(message);
        }
        else
        {
            message="Basariyla guncellendi";
            return new SuccesResult(message);
        }
    }

    @Override
    public Result deleteCustomerById(Object id) {
        int rowsAffected=customerDao.deleteCustomerById(id);
        String message;
        if (rowsAffected ==0)
        {
            message="Silme islemi basarisiz.";
            return new ErrorResult(message);
        }
        else
        {
            message="Silindi.";
            return new SuccesResult(message);
        }
    }
}
