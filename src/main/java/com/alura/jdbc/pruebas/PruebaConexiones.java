package com.alura.jdbc.pruebas;

import com.alura.jdbc.FactoryConnection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexiones {
    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        for (int i =0; i < 12; i++){
            Connection conexion = connectionFactory.recuperaConexion();
            System.out.println("Abriendo la conexion" + (i+1));
        }
    }
}
