package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.activity.HistorialPedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;


public class EstadoPedidoReceiver extends BroadcastReceiver {
    PedidoRepository pedidoRepo= new PedidoRepository();
    @Override
    public void onReceive(Context context, Intent intent) {
        Pedido p =pedidoRepo.buscarPorId(intent.getExtras().getInt("idPedido"));
        p.setEstado(Pedido.Estado.ACEPTADO);
        Toast.makeText(context,"Pedido para "+p.getMailContacto()+" ha cambiado a estado "+Pedido.Estado.ACEPTADO,Toast.LENGTH_LONG).show();
    }
}
