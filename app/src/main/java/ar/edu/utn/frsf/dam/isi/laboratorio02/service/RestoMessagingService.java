package ar.edu.utn.frsf.dam.isi.laboratorio02.service;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ar.edu.utn.frsf.dam.isi.laboratorio02.receiver.EstadoPedidoReceiver;

public class RestoMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData()!=null){
            int id=Integer.parseInt(remoteMessage.getData().get("idPedido"));
            enviarNotificacion(id);
        }
    }

    private void enviarNotificacion(int id){
        Intent i= new Intent(RestoMessagingService.this, EstadoPedidoReceiver.class);
        i.setAction("ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_LISTO");
        i.putExtra("idPedido",id);
        sendBroadcast(i);
    }
}
