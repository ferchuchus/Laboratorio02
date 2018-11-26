package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class BaseDatosRepository {
    private static BaseDatosRepository _INSTANCIA_UNICA = null;
    private BaseDatos db;
    private CategoriaDao categoriaDao;
    private ProductoDao productoDao;


    private BaseDatosRepository(Context ctx) {
        db = Room.databaseBuilder(ctx,
                BaseDatos.class, "DataBaseLab04")
                .fallbackToDestructiveMigration()
                .build();
        categoriaDao = db.categoriaDao();
        productoDao = db.productoDao();

    }

    public static BaseDatosRepository getInstance(Context ctx) {
        if (_INSTANCIA_UNICA == null)
            _INSTANCIA_UNICA = new BaseDatosRepository(ctx);
        return _INSTANCIA_UNICA;
    }

    public void borrarTodo() {
        this.db.clearAllTables();
    }

    public CategoriaDao getCategoriaDao() {
        return categoriaDao;
    }

    public ProductoDao getProductoDao() {
        return productoDao;
    }

    public void setCategoriaDao(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    public void setProductoDao(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    public List<Categoria> ListaCategorias() {
        return categoriaDao.getAll();
    }

    public List<Producto> ListaProductos() {
        return productoDao.getAll();
    }

    public void actualizarCategoria(Categoria cat) {
        categoriaDao.update(cat);
    }

    public void actualizarProducto(Producto prod) {
        productoDao.update(prod);
    }

    public void borrarCategoria(Categoria cat) {
        categoriaDao.delete(cat);
    }

    public void borrarProducto(Producto prod) {
        productoDao.delete(prod);
    }


}
