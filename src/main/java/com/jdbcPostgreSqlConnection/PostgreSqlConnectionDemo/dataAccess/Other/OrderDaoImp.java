//package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.Other;
//
//import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.casting.CastingService;
//import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.dataAccess.connection.ConnectionService;
//import com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.entity.Order;
//import org.json.JSONArray;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//
//@Service
//public class OrderDaoImp implements OrderDao{
//
//    private CastingService<Order> castingService;
//    private ConnectionService connectionService;
//
//    @Autowired
//    public OrderDaoImp(CastingService<Order> castingService, ConnectionService connectionService) {
//        this.castingService = castingService;
//        this.connectionService = connectionService;
//    }
//    @Override
//    public JSONArray getOrderById(int id) {
//        String query="select * from Orders where order_id=?";
//        PreparedStatement preparedStatement=connectionService.getPreparedStatement(query);
//        ResultSet resultSet;
//        try {
//            preparedStatement.setInt(1, id);
//             resultSet=preparedStatement.executeQuery();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return castingService.getObjectJsonArray(resultSet);
//    }
//
//    @Override
//    public List<Order> getAllOrders() {
//        String query="select ord.order_id, " +
//                "ord.customer_id,ord.order_date, ord.freight,ord.ship_name," +
//                "ord.ship_country from orders as ord";
//        Statement statement=connectionService.getStatement(query);
//        ResultSet resultSet;
//        try {
//             resultSet=statement.executeQuery(query);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return castingService.getObjectList(resultSet);
//    }
//
//    @Override
//    public void saveOrder(Order order) {
//
//    }
//
//    @Override
//    public void updateOrder(Order order) {
//
//    }
//
//    @Override
//    public void deleteOrder(int id) {
//
//    }
//}
