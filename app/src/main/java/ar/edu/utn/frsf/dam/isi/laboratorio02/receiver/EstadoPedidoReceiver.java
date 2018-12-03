package ar.edu.utn.frsf.dam.isi.laboratorio02.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import ar.edu.utn.frsf.dam.isi.laboratorio02.activity.HistorialPedidoActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.activity.NuevoPedidoActivity;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.BaseDatosRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.DetallePedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;


public class EstadoPedidoReceiver extends BroadcastReceiver {
    // PedidoRepository pedidoRepo = new PedidoRepository();
    private PedidoDao pedDao;
    private Pedido pedido;

    @Override
    public void onReceive(Context context, Intent intent) {
        pedDao = BaseDatosRepository.getInstance(context).getPedidoDao();
        String accion = intent.getAction();
        pedido=buscarPedido(intent.getExtras().getInt("idPedido"), accion,intent,context);
        //  Pedido p = pedidoRepo.buscarPorId(intent.getExtras().getInt("idPedido"));
      //  prueba(pedido,intent,context);
/*        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Intent destino;
        PendingIntent pendingIntent = null;
        NotificationCompat.Builder notification = null;
      /*  switch (intent.getAction()) {
            case "ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_ACEPTADO":
                p.setEstado(Pedido.Estado.ACEPTADO);
                actualizarPedido(p);
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
                actualizarPedido(p);
                destino = new Intent(context, HistorialPedidoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pendingIntent = PendingIntent.getActivity(context, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

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
                actualizarPedido(p);*/
/*        destino = new Intent(context, NuevoPedidoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        destino.putExtra("VER_DETALLE", 1);
        destino.putExtra("ID_PEDIDO", pedido.getId());
        pendingIntent = PendingIntent.getActivity(context, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("PEDIDO CENTRAL:",""+pedido);

        notification = new NotificationCompat.Builder(context, "CANAL01")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Tu pedido esta " + Pedido.Estado.LISTO)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("El costo será de " + pedido.total() + " " +
                                "Previsto el envío para " + sdf.format(pedido.getFecha())))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        //    break;

        //}
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification.build());
*/
    }

    private void prueba(final Pedido p, final Intent i,final Context c){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Intent destino;
                PendingIntent pendingIntent = null;
                NotificationCompat.Builder notification = null;
                destino = new Intent(c, NuevoPedidoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                destino.putExtra("VER_DETALLE", 1);
//                destino.putExtra("ID_PEDIDO", p.getId());
                pendingIntent = PendingIntent.getActivity(c, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

                Log.d("PEDIDO CENTRAL:",""+p);

                notification = new NotificationCompat.Builder(c, "CANAL01")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Tu pedido esta " + Pedido.Estado.LISTO)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("El costo será de " + p.total() + " " +
                                        "Previsto el envío para " + sdf.format(p.getFecha())))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                //    break;

                //}
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
                notificationManager.notify(1, notification.build());
            }
            };
            Thread hiloRest = new Thread(r);
        hiloRest.start();
    }

    private Pedido buscarPedido(final Integer idPedMostrar, final String accion, final Intent i,final Context c) {
        final Pedido[] ped = new Pedido[1];
        final Pedido[] p = new Pedido[1];
        Runnable r = new Runnable() {
            @Override
            public void run() {
                p[0] = pedDao.getPedidoId(idPedMostrar);
                Log.d("PEDIDO BUSCAADOO", "El pedido es: " + p[0]);
                switch (accion) {
                    case "ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_ACEPTADO":
                        p[0].setEstado(Pedido.Estado.ACEPTADO);
                        actualizarPedido(p[0],i,c);
                        ped[0] = p[0];
                        break;

                    case "ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_EN_PREPARACION":
                        p[0].setEstado(Pedido.Estado.EN_PREPARACION);
                        actualizarPedido(p[0],i,c);
                        ped[0] = p[0];
                        break;

                    case "ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_LISTO":
                        p[0].setEstado(Pedido.Estado.LISTO);
                        actualizarPedido(p[0],i,c);
                        ped[0] = p[0];
                        break;

                }
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
        return ped[0];
    }

    private void actualizarPedido(final Pedido ped, final Intent i,final Context c) {
        Log.d("PEDIDO RECIBIDO", "ES:" + ped);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                pedDao.update(ped);
                prueba(ped,i,c);
            }

        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }

}
