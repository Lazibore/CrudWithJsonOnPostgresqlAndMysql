package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.sql.Date;

@Table(name="orders")
public class Order {

    @Id
    @Column ("order_id")
    private int order_id;

    @Column ("customer_id")
    private  String customer_id;

    @Column ("employee_id")
    private  int employee_id;

    @Column("shipped_date")
    private Date shipped_date;

    @Column("ship_name")
    private  String ship_name;

    @Column("freight")
    private  double freight;

    public Order(){}
    public Order(int order_id, String customer_id, int employee_id, Date shipped_date, String ship_name, double freight) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.shipped_date = shipped_date;
        this.ship_name = ship_name;
        this.freight = freight;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public Date getShipped_date() {
        return shipped_date;
    }

    public void setShipped_date(Date shipped_date) {
        this.shipped_date = shipped_date;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }
}