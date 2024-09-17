package com.jdbcPostgreSqlConnection.PostgreSqlConnectionDemo.exceptions;


// Belirli musterilerin siparis miktari siniri vardir.

public enum CustomerFreightCapacities {

        VICTE (20),
        HANAR(30),
        LILAS(15),
         WELLI(10);

        private final int freight;

    CustomerFreightCapacities(int freight) {
        this.freight=freight;
    }

    public int getFreight() {
        return freight;
    }

}

