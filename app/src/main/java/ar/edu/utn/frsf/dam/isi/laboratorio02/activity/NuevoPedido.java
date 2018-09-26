package ar.edu.utn.frsf.dam.isi.laboratorio02.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class NuevoPedido extends AppCompatActivity {

    private EditText edtPedidoCorreo;
    private RadioGroup optPedidoModoEntrega;
    private RadioButton optPedidoRetira;
    private RadioButton optPedidoEnvio;
    private EditText edtPedidoDireccion;
    private EditText edtHora;
    private ListView lstPedidoItem;
    private Button btnAgregarProducto;
    private Button btnQuitarProducto;
    private TextView lblPedido;
    private Button btnHacerPedido;
    private Button btnVolver;
    private Pedido unPedido;
    private PedidoRepository repositorioPedido;
    private ProductoRepository repositorioProducto;
    private ArrayAdapter<PedidoDetalle> adaptadorLstProductoItem;
    private Integer idPedidoMostrar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);
        edtPedidoCorreo = (EditText) findViewById(R.id.edtPedidoCorreo);
        optPedidoModoEntrega = (RadioGroup) findViewById(R.id.optPedidoModoEntrega);
        optPedidoRetira = (RadioButton) findViewById(R.id.optPedidoRetira);
        optPedidoEnvio = (RadioButton) findViewById(R.id.optPedidoEnvio);
        edtPedidoDireccion = (EditText) findViewById(R.id.edtPedidoDireccion);
        edtHora = (EditText) findViewById(R.id.edtPedidoHoraEntrega);
        lstPedidoItem = (ListView) findViewById(R.id.lstPedidoItem);
        btnAgregarProducto = (Button) findViewById(R.id.btnPedidoAddProducto);
        btnQuitarProducto = (Button) findViewById(R.id.btnPedidoQuitarProducto);
        lblPedido = (TextView) findViewById(R.id.lblPedido);
        btnHacerPedido = (Button) findViewById(R.id.btnHacerPedido);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        edtPedidoDireccion.setEnabled(false);
        optPedidoRetira.setChecked(true);
        String[] horaIngresada = edtHora.getText().toString().split(":");
        final GregorianCalendar hora = new GregorianCalendar();
        final int valorHora = Integer.valueOf(horaIngresada[0]);
        final int valorMinutos = Integer.valueOf(horaIngresada[1]);

        Intent i = getIntent();
        if (i.getExtras() != null) {
            idPedidoMostrar = i.getExtras().getInt("idPedido");
        }
        if (idPedidoMostrar > 0) {
            unPedido = repositorioPedido.buscarPorId(idPedidoMostrar);
            adaptadorLstProductoItem = new ArrayAdapter<PedidoDetalle>(NuevoPedido.this, android.R.layout.simple_list_item_single_choice, unPedido.getDetalle());
            lstPedidoItem.setAdapter(adaptadorLstProductoItem);
            edtPedidoCorreo.setText(unPedido.getMailContacto());
            if (unPedido.getRetirar() == true) {
                optPedidoRetira.setActivated(true);
                optPedidoEnvio.setActivated(false);
                edtPedidoDireccion.setEnabled(false);
            } else {
                optPedidoRetira.setActivated(false);
                optPedidoEnvio.setActivated(true);
                edtPedidoDireccion.setText(unPedido.getDireccionEnvio());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            edtHora.setText(sdf.format(unPedido.getFecha()));
        } else {
            unPedido = new Pedido();
            repositorioPedido = new PedidoRepository();
            repositorioProducto = new ProductoRepository();
            adaptadorLstProductoItem = new ArrayAdapter<PedidoDetalle>(NuevoPedido.this, android.R.layout.simple_list_item_single_choice, unPedido.getDetalle());
            lstPedidoItem.setAdapter(adaptadorLstProductoItem);
        }


        optPedidoRetira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPedidoDireccion.setEnabled(false);
            }
        });

        optPedidoEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPedidoDireccion.setEnabled(true);
            }
        });

        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevoPedido.this, ListaProducto.class);
                i.putExtra("NUEVO_PEDIDO", 1);
                startActivityForResult(i, 1);
            }
        });

        btnHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validacion() == true) {
                    hora.set(Calendar.HOUR_OF_DAY, valorHora);
                    hora.set(Calendar.MINUTE, valorMinutos);
                    hora.set(Calendar.SECOND, Integer.valueOf(0));
                    unPedido.setFecha(hora.getTime());
                    unPedido.setMailContacto(edtPedidoCorreo.toString());
                    unPedido.setRetirar(optPedidoRetira.isChecked());
                    unPedido.setDireccionEnvio(edtPedidoDireccion.toString());
                    unPedido.setEstado(Pedido.Estado.REALIZADO);
                    repositorioPedido.guardarPedido(unPedido);

                    Intent i = new Intent(getApplicationContext(), HistorialPedido.class);
                    startActivity(i);
                }

            }

            public Boolean validacion() {
                if (edtPedidoCorreo.getText().toString().isEmpty()) {
                    Toast.makeText(NuevoPedido.this,
                            "Debe ingresar el correo", Toast.LENGTH_LONG).show();
                    return false;
                } else if (optPedidoEnvio.isChecked() && edtPedidoDireccion.getText().toString().isEmpty()) {
                    Toast.makeText(NuevoPedido.this,
                            "Debe ingresar la direccion", Toast.LENGTH_LONG).show();
                    return false;
                } else if (lstPedidoItem.getAdapter().getCount() == 0) {
                    Toast.makeText(NuevoPedido.this,
                            "Debe agregar al menos un producto", Toast.LENGTH_LONG).show();
                    return false;
                } else if (valorHora < 0 || valorHora > 23) {
                    Toast.makeText(NuevoPedido.this,
                            "La hora ingresada " + valorHora + " es incorrecta", Toast.LENGTH_LONG).show();
                    return false;
                } else if (valorMinutos < 0 || valorMinutos > 59) {
                    Toast.makeText(NuevoPedido.this,
                            "Los minutos " + valorMinutos + " son incorrectos", Toast.LENGTH_LONG).show();
                    return false;
                } else
                    return true;
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int requestResult, @Nullable Intent data) {
        if (requestCode == 1 && requestResult == RESULT_OK) {
            int idProducto = data.getExtras().getInt("idProducto");
            Producto item = repositorioProducto.buscarPorId(idProducto);
            int cantidad = data.getExtras().getInt("cantidad");
            PedidoDetalle detallePedido = new PedidoDetalle(cantidad, item);
            detallePedido.setPedido(unPedido);
            double precioTotal = unPedido.total();
            lblPedido.setText("Total del Pedido: $" + precioTotal);
            adaptadorLstProductoItem.notifyDataSetChanged();
        }
    }

}
