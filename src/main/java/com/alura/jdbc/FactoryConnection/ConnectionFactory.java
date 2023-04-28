package com.alura.jdbc.FactoryConnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import jdk.jfr.DataAmount;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private DataSource dataSource;
 public ConnectionFactory(){
 var pooledDataSource = new ComboPooledDataSource();
 pooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3339/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
 pooledDataSource.setUser("root");
 pooledDataSource.setPassword("12345");
//setearemos la cantidad maxima
 pooledDataSource.setMaxPoolSize(10);

 this.dataSource = pooledDataSource;
 }
    public Connection recuperaConexion() {
       try {
           return this.dataSource.getConnection();
       }catch (SQLException e){
           throw  new RuntimeException(e);
       }
    }
}
