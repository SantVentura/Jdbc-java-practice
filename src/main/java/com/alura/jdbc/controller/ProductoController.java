package com.alura.jdbc.controller;
import com.alura.jdbc.Dao.ProductoDao;
import com.alura.jdbc.FactoryConnection.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import java.util.List;

public class ProductoController {

	private final com.alura.jdbc.Dao.ProductoDao ProductoDao;

	public  ProductoController() {
		var factory = new ConnectionFactory();
		this.ProductoDao = new ProductoDao(factory.recuperaConexion());
	}
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		return ProductoDao.modificar(nombre, descripcion, cantidad, id);
	}
	public int eliminar(Integer id) {
		return ProductoDao.eliminar(id);
	}

	public List<Producto> listar(){
    return ProductoDao.listar();
	}

	public List<Producto> listar(Categoria categoria){
		return ProductoDao.listar(categoria.getId());
	}
	public  void guardar(Producto producto, Integer categoriaId) {
		producto.setCategoriaId(categoriaId);
		ProductoDao.guardarP(producto);
	}



}
