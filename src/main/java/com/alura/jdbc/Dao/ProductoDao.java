package com.alura.jdbc.Dao;


import com.alura.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;


//El patrón Data Access Object (DAO) pretende principalmente independizar
// la aplicación de la forma de acceder a la base de datos, o cualquier otro tipo de repositorio de datos
public class ProductoDao {
    private final Connection con;

    public ProductoDao(Connection con) {
        this.con = con;
    }
    public List<Producto> listar() {
        List<Producto> resultado = new ArrayList<>();
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "SELECT id, nombre, descripcion, cantidad  FROM  producto");
            try (statement) {
                statement.execute();
                //para tomar los datos
                final ResultSet resultSet = statement.getResultSet();
                try (resultSet) {
                    while (resultSet.next()) {
                        //los llamamos por nombre de columna o index
                        resultado.add(new Producto(resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("descripcion"),
                                resultSet.getInt("cantidad")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
    public void guardarP(Producto producto) {
        try  {
            PreparedStatement statement;
            statement = con.prepareStatement(
                    "INSERT INTO producto "
                            + "(nombre, descripcion, cantidad, categoria_id)"
                            + " VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            try (statement) {
                statement.setString(1, producto.getNombre());
                statement.setString(2, producto.getDescripcion());
                statement.setInt(3, producto.getCantidad());
                statement.setInt(4,producto.getCategoriaId());

                statement.execute();

                final ResultSet resultSet = statement.getGeneratedKeys();

                try (resultSet) {
                    while (resultSet.next()) {
                        producto.setId(resultSet.getInt(1));

                        System.out.printf("Fue insertado el producto de %s ", producto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE producto SET "
                            + " nombre = ?, "
                            + " descripcion = ?,"
                            + " cantidad = ?"
                            + " WHERE id = ?");

            try (statement) {
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int eliminar(Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE id = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();
                int updateCount = statement.getUpdateCount();
                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar(Integer categoriaId) {
        List<Producto> resultado = new ArrayList<>();
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "SELECT id, nombre, descripcion, cantidad  FROM  producto " + " WHERE categoria_id = ?");
            try (statement) {
                statement.setInt(1,categoriaId);
                statement.execute();
                //para tomar los datos
                final ResultSet resultSet = statement.getResultSet();
                try (resultSet) {
                    while (resultSet.next()) {
                        //los llamamos por nombre de columna o index
                        resultado.add(new Producto(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("descripcion"),
                                resultSet.getInt("cantidad")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
