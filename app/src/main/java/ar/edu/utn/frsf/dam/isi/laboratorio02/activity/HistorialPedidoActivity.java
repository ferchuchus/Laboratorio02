package ar.edu.utn.frsf.dam.isi.laboratorio02.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.adapter.PedidoAdapter;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.BaseDatosRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;


public class HistorialPedidoActivity extends AppCompatActivity {

    private Button btnNuevo;
    private Button btnMenu;
    private ArrayAdapter<Pedido> pedidoArrayAdapter;
    private PedidoRepository repositorioPedido = new PedidoRepository();
    private ListView listaPedidos;
    private PedidoDao pedDao;
    private List<Pedido> pedidoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedido);
        pedDao = BaseDatosRepository.getInstance(this).getPedidoDao();

        btnNuevo = (Button) findViewById(R.id.btnHistorialNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NuevoPedidoActivity.class);
                i.putExtra("VER_DETALLE", 0);
                startActivity(i);
            }
        });

        btnMenu = (Button) findViewById(R.id.btnHistorialMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        buscarPedidos();
       /*if (!repositorioPedido.getLista().isEmpty()) {
            listaPedidos = (ListView) findViewById(R.id.lstHistorialPedidos);
            listaPedidos.setAdapter(new PedidoAdapter(this, repositorioPedido.getLista()));

        }*/
    }

    private void buscarPedidos() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                pedidoList = pedDao.getAll();
                if (!pedidoList.isEmpty()) {
                listaPedidos = (ListView) findViewById(R.id.lstHistorialPedidos);
                listaPedidos.setAdapter(new PedidoAdapter(getApplicationContext(), pedidoList));
                }
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }
}
