package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.controller;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.business.order.OrderService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.DataResult;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getallorders")
    public ResponseEntity<?> getAllOrders()
    {
         return ResponseEntity.ok(this.orderService.getAllOrders());
    }
    @GetMapping(value = "/getorderbyid/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Object id)
    {
       return ResponseEntity.ok(orderService.getOrderById(id));
    }
    @DeleteMapping ("/delorderbyid/{id}")
    public Result deleteOrderById(@PathVariable("id") int id)
    {
        return orderService.deleteOrderById(id);
    }
    @PostMapping  ("/saveorder")
    public  ResponseEntity<DataResult<Object>> saveOrder(@RequestBody String jsonInsertString)
    {
        JSONObject order=new JSONObject(jsonInsertString);
        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    @PostMapping("updateorder")
    public ResponseEntity<Result> updateOrder(@RequestBody String jsonUpdateString)
    {
        JSONObject order=new JSONObject(jsonUpdateString);
       return ResponseEntity.ok(orderService.updateOrder(order));
    }
}
