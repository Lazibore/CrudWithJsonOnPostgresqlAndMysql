package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.controller;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.business.order.OrderService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.DataResult;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getallorders")
    public ResponseEntity<?> getAllOrders() throws ExecutionException, InterruptedException {
        Future<DataResult<List<Map<String,Object>>>> futureOrders=this.orderService.getAllOrders();
         return ResponseEntity.ok(futureOrders.get());
    }
    @GetMapping(value = "/getorderbyid/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Object id)
            throws ExecutionException, InterruptedException
    {
        Future<DataResult<Map<String, Object>>> futureOrder= orderService.getOrderById(id);
       return ResponseEntity.ok(futureOrder.get());
    }
    @DeleteMapping ("/delorderbyid/{id}")
    public Result deleteOrderById(@PathVariable("id") int id)
            throws ExecutionException, InterruptedException
    {
        Future<Result> futureResult=orderService.deleteOrderById(id);
        return futureResult.get();
    }

    @PostMapping  ("/saveorder")
    public  ResponseEntity<DataResult<Object>> saveOrder(@RequestBody String jsonInsertString)
            throws ExecutionException, InterruptedException
    {
        JSONObject order=new JSONObject(jsonInsertString);
        Future<DataResult<Object>> futureMap=orderService.saveOrder(order);
        return ResponseEntity.ok(futureMap.get());
    }

    @PutMapping("updateorder")
    public ResponseEntity<Result> updateOrder(@RequestBody String jsonUpdateString)
            throws ExecutionException, InterruptedException
    {
        JSONObject order=new JSONObject(jsonUpdateString);
        Future<Result> futureResult=orderService.updateOrder(order);
       return ResponseEntity.ok(futureResult.get());
    }
}
