package ar.edu.utn.frsf.dam.isi.laboratorio02.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.adapter.PedidoAdapter;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;



public class HistorialPedido extends AppCompatActivity {

    private Button btnNuevo;
    private Button btnMenu;
    private ArrayAdapter<Pedido> pedidoArrayAdapter;
    private PedidoRepository repositorioPedido= new PedidoRepository();
    private ListView listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedido);

        btnNuevo = (Button) findViewById(R.id.btnHistorialNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NuevoPedido.class);
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

        if(!repositorioPedido.getLista().isEmpty()) {
            listaPedidos=(ListView)findViewById(R.id.lstHistorialPedidos);
            listaPedidos.setAdapter(new PedidoAdapter(this, repositorioPedido.getLista()));

        }

    }


}
