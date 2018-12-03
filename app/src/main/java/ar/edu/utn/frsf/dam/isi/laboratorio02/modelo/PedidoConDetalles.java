package ar.edu.utn.frsf.dam.isi.laboratorio02.modelo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class PedidoConDetalles {

    @Embedded
    public Pedido pedido;

    @Relation(parentColumn = "id", entityColumn = "id", entity = DetallePedido.class)
    public List<DetallePedido> detalle;

}
