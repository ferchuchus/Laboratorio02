package ar.edu.utn.frsf.dam.isi.laboratorio02.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;

import ar.edu.utn.frsf.dam.isi.laboratorio02.activity.HistorialPedidoActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.activity.NuevoPedidoActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;


public class EstadoPedidoReceiver extends BroadcastReceiver {
    PedidoRepository pedidoRepo = new PedidoRepository();

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Pedido p = pedidoRepo.buscarPorId(intent.getExtras().getInt("idPedido"));
        Intent destino;
        PendingIntent pendingIntent = null;
        NotificationCompat.Builder notification = null;

        switch (intent.getAction()){
            case "ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_ACEPTADO":
                p.setEstado(Pedido.Estado.ACEPTADO);
                destino = new Intent(context, NuevoPedidoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                destino.putExtra("VER_DETALLE", 1);
                destino.putExtra("ID_PEDIDO", p.getId());
                pendingIntent = PendingIntent.getActivity(context, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

                notification = new NotificationCompat.Builder(context, "CANAL01")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Tu pedido fue " + Pedido.Estado.ACEPTADO)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("El costo será de " + p.total() + " " +
                                        "Previsto el envío para " + sdf.format(p.getFecha())))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                break;

            case "ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_EN_PREPARACION":
                p.setEstado(Pedido.Estado.EN_PREPARACION);
                destino= new Intent(context, HistorialPedidoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pendingIntent=PendingIntent.getActivity(context,0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

                notification = new NotificationCompat.Builder(context, "CANAL01")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Tu pedido esta " + Pedido.Estado.EN_PREPARACION)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("El costo será de " + p.total() + " " +
                                        "Previsto el envío para " + sdf.format(p.getFecha())))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                break;

            case "ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_LISTO":
                p.setEstado(Pedido.Estado.LISTO);
                destino = new Intent(context, NuevoPedidoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                destino.putExtra("VER_DETALLE", 1);
                destino.putExtra("ID_PEDIDO", p.getId());
                pendingIntent = PendingIntent.getActivity(context, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

                notification = new NotificationCompat.Builder(context, "CANAL01")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Tu pedido esta " + Pedido.Estado.LISTO)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("El costo será de " + p.total() + " " +
                                        "Previsto el envío para " + sdf.format(p.getFecha())))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                break;

        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification.build());


    }
}
