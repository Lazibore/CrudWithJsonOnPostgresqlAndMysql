package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.exceptions;

public class OrderFreightOverflowException extends RuntimeException  {

    public OrderFreightOverflowException (String message)
    {
        super(message);
    }
    public static OrderFreightOverflowException checkCustomerFreight
            (String customerId,int freight)
    {

       return new OrderFreightOverflowException
               ("{"+customerId +" } customerId sine sahip olan musteri  siparis miktari " +
                       "olan {" + freight+ " kg} sinirini asmistir");

    }

}
