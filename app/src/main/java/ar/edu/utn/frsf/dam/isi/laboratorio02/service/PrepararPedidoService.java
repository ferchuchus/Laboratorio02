package ar.edu.utn.frsf.dam.isi.laboratorio02.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.BaseDatosRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PrepararPedidoService extends IntentService {
    private PedidoDao pedDao;
    private List<Pedido> listaPedidos;

    public PrepararPedidoService() {
    super("PrepararPedidoService");
    }

    @Override
    protected  void onHandleIntent(Intent intet){
        pedDao = BaseDatosRepository.getInstance(this).getPedidoDao();
        try{
            Thread.currentThread().sleep(20000);
        }catch (Exception e){
            e.printStackTrace();
        }
    //    PedidoRepository repositorioPedidos= new PedidoRepository();
      //  List<Pedido> listaPedidos= repositorioPedidos.getLista();
        buscarPedidos();
        for(Pedido p : listaPedidos){
            if(p.getEstado().equals(Pedido.Estado.ACEPTADO)){
                Intent i= new Intent();
                i.putExtra("idPedido", p.getId());
                i.setAction("ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_EN_PREPARACION");
                sendBroadcast(i);
            }


        }

    }
    private void buscarPedidos() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                listaPedidos = pedDao.getAll();
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }
}
