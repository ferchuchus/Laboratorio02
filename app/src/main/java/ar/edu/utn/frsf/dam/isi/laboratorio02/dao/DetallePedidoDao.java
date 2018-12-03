package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.DetallePedido;


@Dao
public interface DetallePedidoDao {

    @Query("SELECT * FROM DetallePedido WHERE id=:id")
    DetallePedido getDetalleId(Integer id);

    @Query("SELECT * FROM DetallePedido")
    List<DetallePedido> getAll();

    @Insert
    long insert(DetallePedido det);

    @Update
    void update(DetallePedido det);

    @Delete
    void delete(DetallePedido det);
}
