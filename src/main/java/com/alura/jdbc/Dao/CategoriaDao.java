package com.alura.jdbc.Dao;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao {

    private Connection con;

    public  CategoriaDao(Connection connection) {
        this.con = connection;
    }

    public  List<Categoria>listar() {
        List<Categoria> resultado = new ArrayList<>();
        try {
             final PreparedStatement statement = con.prepareStatement(
                     "SELECT id , nombre FROM categoria");
        try (statement){
            final ResultSet executeQuery = statement.executeQuery();
            try (executeQuery) {
                while (executeQuery.next()) {
                    var categoria = new Categoria(executeQuery.getInt("id"),
                            executeQuery.getString("nombre"));
                    resultado.add(categoria);
                }
            }
        }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return resultado;
    }

    public List<Categoria> listarConProductos() {
        List<Categoria> resultado = new ArrayList<>();
        try {
            var querySelect = "SELECT C.id, C.nombre ,P.id, P.nombre, P.cantidad "
                    +  " FROM categoria C "
                    + "INNER JOIN producto P ON C.id = P.categoria_id";
            System.out.println(querySelect);
            final PreparedStatement statement = con.prepareStatement(querySelect);
            try (statement){
                final ResultSet resultSet = statement.executeQuery();
                try (resultSet) {
                    while (resultSet.next()) {
                        Integer categoriaId = resultSet.getInt("C.id");
                        String categoriaNombre = resultSet.getString("C.nombre");

                        var categoria = resultado
                                .stream().filter(cat -> cat.getId().equals(categoriaId))
                                .findAny().orElseGet(() -> {
                                    Categoria cat = new Categoria(categoriaId, categoriaNombre);
                                    resultado.add(cat);

                                    return cat;
                                });

                        Producto producto = new Producto(resultSet.getInt("P.id"),
                                resultSet.getString("P.nombre"),
                                resultSet.getInt("P.cantidad"));
                            categoria.agregar(producto);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
      return resultado;
    }
}
