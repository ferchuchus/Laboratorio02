package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PrepararPedidoService extends IntentService {
    public PrepararPedidoService() {
    super("PrepararPedidoService");
    }

    @Override
    protected  void onHandleIntent(Intent intet){
        try{
            Thread.currentThread().sleep(20000);
        }catch (Exception e){
            e.printStackTrace();
        }
        PedidoRepository repositorioPedidos= new PedidoRepository();
        List<Pedido> listaPedidos= repositorioPedidos.getLista();
        for(Pedido p : listaPedidos){
            if(p.getEstado().equals(Pedido.Estado.ACEPTADO)){
                p.setEstado(Pedido.Estado.EN_PREPARACION);
                Intent i= new Intent();
                i.putExtra("idPedido", p.getId());
                i.setAction("ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_EN_PREPARACION");
                sendBroadcast(i);
            }


        }

    }
}
