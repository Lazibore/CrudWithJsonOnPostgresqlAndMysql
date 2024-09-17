package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.controller;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.business.customer.CustomerService;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.DataResult;
import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.core.result.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getallcustomers")
    public ResponseEntity<?> getAllCustomers()
    {
        return ResponseEntity.ok(this.customerService.getAllCustomers());
    }
    @GetMapping(value = "/getcustomerbyid/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Object id)
    {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
    @DeleteMapping("/delcustomerbyid/{id}")
    public Result deleteCustomerById(@PathVariable("id") Object id)
    {
        return customerService.deleteCustomerById(id);
    }

    @PostMapping("/savecustomer")
    public  ResponseEntity<DataResult<Object>> saveCustomer(@RequestBody String jsonInsertString)
    {
        JSONObject customer=new JSONObject(jsonInsertString);
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }

    @PutMapping("updatecustomer")
    public ResponseEntity<Result> updateCustomer(@RequestBody String jsonUpdateString)
    {
        JSONObject customer=new JSONObject(jsonUpdateString);
        return ResponseEntity.ok(customerService.updateCustomer(customer));
    }

}
