package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;

import ar.edu.utn.frsf.dam.isi.laboratorio02.activity.HistorialPedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.activity.NuevoPedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;


public class EstadoPedidoReceiver extends BroadcastReceiver {
    PedidoRepository pedidoRepo = new PedidoRepository();

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Pedido p = pedidoRepo.buscarPorId(intent.getExtras().getInt("idPedido"));

        if(intent.getAction().equals("ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_ACEPTADO")) {
            p.setEstado(Pedido.Estado.ACEPTADO);
            Intent destino = new Intent(context, NuevoPedido.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            destino.putExtra("VER_DETALLE", 1);
            destino.putExtra("ID_PEDIDO", p.getId());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "CANAL01")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Tu pedido fue " + Pedido.Estado.ACEPTADO)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("El costo será de " + p.total() + " " +
                                    "Previsto el envío para " + sdf.format(p.getFecha())))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, notification.build());

        }
        else if (intent.getAction().equals("ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_EN_PREPARACION")){
            Intent destino= new Intent(context, HistorialPedido.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "CANAL01")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Tu pedido esta " + Pedido.Estado.EN_PREPARACION)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("El costo será de " + p.total() + " " +
                                    "Previsto el envío para " + sdf.format(p.getFecha())))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, notification.build());

        }


    }
}
