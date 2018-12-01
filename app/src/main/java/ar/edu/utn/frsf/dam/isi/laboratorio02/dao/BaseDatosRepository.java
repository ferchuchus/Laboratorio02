package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class BaseDatosRepository {
    private static BaseDatosRepository _INSTANCIA_UNICA = null;
    private BaseDatos db;
    private CategoriaDao categoriaDao;
    private ProductoDao productoDao;
    private PedidoDao pedidoDao;


    private BaseDatosRepository(Context ctx) {
        db = Room.databaseBuilder(ctx,
                BaseDatos.class, "DataBaseLab04")
                .fallbackToDestructiveMigration()
                .build();
        categoriaDao = db.categoriaDao();
        productoDao = db.productoDao();
        pedidoDao = db.pedidoDao();

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

    public PedidoDao getPedidoDao() {
        return pedidoDao;
    }

    public void setCategoriaDao(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    public void setProductoDao(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    public void setPedidoDao(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }

    public List<Categoria> ListaCategorias() {
        return categoriaDao.getAll();
    }

    public List<Producto> ListaProductos() {
        return productoDao.getAll();
    }

    public List<Pedido> ListaPedidos() {
        return pedidoDao.getAll();
    }

    public void actualizarCategoria(Categoria cat) {
        categoriaDao.update(cat);
    }

    public void actualizarPedido(Pedido pedido) {
        pedidoDao.update(pedido);
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

    public void borrarPedido(Pedido pedido) {
        pedidoDao.delete(pedido);
    }

    public Producto buscarProductoID(Integer id) {
        return productoDao.getProductoId(id);
    }

    public Pedido buscarPedidoID(Integer id) {
        return pedidoDao.getPedidoId(id);
    }

}
