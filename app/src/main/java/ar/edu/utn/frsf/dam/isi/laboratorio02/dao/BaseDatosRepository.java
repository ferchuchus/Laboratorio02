package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.DetallePedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class BaseDatosRepository {
    private static BaseDatosRepository _INSTANCIA_UNICA = null;
    private BaseDatos db;
    private CategoriaDao categoriaDao;
    private ProductoDao productoDao;
    private PedidoDao pedidoDao;
    private DetallePedidoDao detalleDao;


    private BaseDatosRepository(Context ctx) {
        db = Room.databaseBuilder(ctx,
                BaseDatos.class, "DataBaseLab04")
                .fallbackToDestructiveMigration()
                .build();
        categoriaDao = db.categoriaDao();
        productoDao = db.productoDao();
        pedidoDao = db.pedidoDao();
        detalleDao = db.detallePedidoDao();

    }

    public static BaseDatosRepository getInstance(Context ctx) {
        if (_INSTANCIA_UNICA == null)
            _INSTANCIA_UNICA = new BaseDatosRepository(ctx);
        return _INSTANCIA_UNICA;
    }


    public CategoriaDao getCategoriaDao() {
        return categoriaDao;
    }

    public ProductoDao getProductoDao() {
        return productoDao;
    }

    public PedidoDao getPedidoDao() {
        return pedidoDao;
    }

    public DetallePedidoDao getDetalleDao() {
        return detalleDao;
    }
}