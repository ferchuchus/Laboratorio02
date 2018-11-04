package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Path;

public interface ProductoRetrofit {
    @_______("_______/")
    public Call<List<Producto>> listarProductos();

    @_______("_______/{id}")
    public Call<Producto> buscarProductoPorId(@Path("id") int idProducto);

    @_______("_______/")
    Call<Producto> crearProducto(@Body Producto p);

    @_______("_______/{id}")
    Call<Producto> actualizarProducto(@Path("id") int idProducto, @Body Producto p);

    @_______("_______/{id}")
    Call<Producto> borrar(@Path("id") int idProducto);
}
