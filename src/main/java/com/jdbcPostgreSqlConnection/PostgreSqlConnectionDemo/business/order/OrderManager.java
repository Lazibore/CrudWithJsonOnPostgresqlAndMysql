package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.business.order;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.*;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.order.OrderDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderManager implements OrderService{
    @Autowired
    private OrderDao orderDao;

    @Override
    public DataResult<Map<String, Object>> getOrderById(Object id)
    {
        Map<String, Object> islem=orderDao.getOrderById(id);
       if(islem==null)
       {
           return new ErrorDataResult<>("Siparis Bulunamadi");
       }
        return new SuccessDataResult<>(islem,"Islem basarili");
    }
    @Override
    public DataResult<List<Map<String,Object>>> getAllOrders() {
        List<Map<String,Object>> orders=orderDao.getAllOrders();
        if (orders.isEmpty())
        {
            return new ErrorDataResult<>("Siparis bulunamadi.");
        }
        else
        {
            return new SuccessDataResult<>(orders,"Islem basarili");
        }

    }

    @Override
    public DataResult<Object> saveOrder(JSONObject order) {
        Object savedOrderId =orderDao.saveOrder(order);
        if (savedOrderId=="")
        {
            return new ErrorDataResult<>("Ekleme islemi sirasinda bir hata olustu");
        }
        else if(savedOrderId==null)
        {
            return new ErrorDataResult<>("Kaydetme islemini kontrol edin");
        }
        else
        {
            return new SuccessDataResult<>(savedOrderId,"Ekleme islemi basarili");
        }
    }

    @Override
    public Result updateOrder(JSONObject order) {
        int rowsAffected=orderDao.updateOrder(order);
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
    public Result deleteOrderById(int id) {
        int rowsAffected=orderDao.deleteOrderById(id);
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
