package com.alura.jdbc.controller;

import com.alura.jdbc.Dao.CategoriaDao;
import com.alura.jdbc.FactoryConnection.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private CategoriaDao categoriaDao;
    public CategoriaController(){
        var factory = new ConnectionFactory();
        this.categoriaDao = new CategoriaDao(factory.recuperaConexion());
    }

	public List<Categoria> listar() {
        return categoriaDao.listar();
	}

    public List<Categoria> cargaReporte() {
        return this.categoriaDao.listarConProductos();
    }

}
