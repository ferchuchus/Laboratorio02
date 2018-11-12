package ar.edu.utn.frsf.dam.isi.laboratorio02.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ar.edu.utn.frsf.dam.isi.laboratorio02.service.PrepararPedidoService;
import ar.edu.utn.frsf.dam.isi.laboratorio02.R;


public class MainActivity extends AppCompatActivity {

    private Button btnNuevoPedido;
    private Button btnHistorial;
    private Button btnListaProductos;
    private Button btnPrepararPedidos;
    private Button btnConfiguracion;
    private Button btnCategoria;
    private Button btnProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        if (getIntent().getExtras() != null) {
            int idPedido = Integer.parseInt(getIntent().getExtras().getString("idPedido"));
            Intent i = new Intent();
            i.putExtra("idPedido", idPedido);
            i.setAction("ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_LISTO");
            sendBroadcast(i);
        }

        btnNuevoPedido = (Button) findViewById(R.id.btnMainNuevoPedido);
        btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NuevoPedido.class);
                i.putExtra("VER_DETALLE",0);
                startActivity(i);
            }
        });

        btnHistorial = (Button) findViewById(R.id.btnHistorialPedidos);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HistorialPedido.class);
                startActivity(i);
            }
        });

        btnListaProductos = (Button) findViewById(R.id.btnListaProductos);
        btnListaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ListaProducto.class);
                i.putExtra("NUEVO_PEDIDO",0);
                startActivity(i);
            }
        });

        btnPrepararPedidos= (Button) findViewById(R.id.btnPrepararPedidos);
        btnPrepararPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), PrepararPedidoService.class);
                startService(i);
            }
        });

        btnConfiguracion= (Button) findViewById(R.id.btnConfiguracion);
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), ConfiguracionActivity.class);
                startActivity(i);
            }
        });

        btnCategoria= (Button) findViewById(R.id.btnCategorias);
        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), CategoriaActivity.class);
                startActivity(i);
            }
        });

        btnProducto= (Button) findViewById(R.id.btnProducto);
        btnProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), GestionProductoActivity.class);
                startActivity(i);
            }
        });
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name= getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel= new NotificationChannel("CANAL01", name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
