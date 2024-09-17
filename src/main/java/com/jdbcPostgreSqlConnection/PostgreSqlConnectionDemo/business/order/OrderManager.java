package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.business.order;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.*;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.data.order.OrderDao;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.exceptions.CustomerFreightCapacities;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.exceptions.OrderFreightOverflowException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class OrderManager implements OrderService{
    @Autowired
    private OrderDao orderDao;

    private final ExecutorService executorService=
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);


    @Override
    public Future<DataResult<Map<String, Object>>> getOrderById(Object id)
    {
        return executorService.submit(()->{
            Map<String, Object> order=orderDao.getOrderById(id);
            if(order==null)
            {
                return new ErrorDataResult<>("Order not found.");
            }
            return new SuccessDataResult<>(order,"Order found.");
        });
    }
    @Override
    public Future<DataResult<List<Map<String,Object>>>> getAllOrders() {

     return executorService.submit(() -> {
           List<Map<String,Object>> orders=orderDao.getAllOrders();
           if (orders.isEmpty())
           {
               return new ErrorDataResult<>("Order not found.");
           }
           else
           {
               return new SuccessDataResult<>(orders,"Orders listed");
           }
       });
    }

    @Override
    public Future<DataResult<Object>> saveOrder(JSONObject order) {

        return executorService.submit(()->{
            // Kaydedilen Objenin ID sini donduruyor.
            Object savedOrderId =orderDao.saveOrder(order);
            if (savedOrderId.equals(0))
            {
                return new ErrorDataResult<>("Not saved");
            }
            else
            {
                return new SuccessDataResult<>(savedOrderId,"Saved successfully");
            }
        });

    }

    @Override
    public Future<Result> updateOrder(JSONObject order) {
        return executorService.submit(()->{
            int rowsAffected=0;
            String message;
            String customerId=order.getString("customer_id");
            int freight=order.getInt("freight");

            try
            {
                // Bazi musterilerin urun miktar kisiti bulundugundan bunun kontrolu yapiliyor.
                if (freight !=0 && customerId !=null)
                {
                    CustomerFreightCapacities cfc= CustomerFreightCapacities.valueOf(customerId);
                    if (freight>=cfc.getFreight())
                    {
                        throw OrderFreightOverflowException.checkCustomerFreight(customerId,freight);
                    }
                    else
                        rowsAffected=orderDao.updateOrder(order);
                }
            }
            catch (OrderFreightOverflowException | IllegalArgumentException ea)
            {
                throw new RuntimeException(ea);
            }
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

        });
    }

    @Override
    public Future<Result> deleteOrderById(int id) {
        return executorService.submit(()->{
            int rowsAffected=orderDao.deleteOrderById(id);
            String message;
            if (rowsAffected ==0)
            {
                message="Not deleted";
                return new ErrorResult(message);
            }
            else
            {
                message="Order deleted";
                return new SuccesResult(message);
            }
        });
    }
}
