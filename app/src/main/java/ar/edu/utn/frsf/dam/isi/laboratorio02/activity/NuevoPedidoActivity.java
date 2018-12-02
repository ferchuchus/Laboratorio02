package ar.edu.utn.frsf.dam.isi.laboratorio02.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.BaseDatosRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.DetallePedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class NuevoPedidoActivity extends AppCompatActivity {

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
    private ArrayAdapter<DetallePedido> adaptadorLstProductoItem;
    private int idPedidoMostrar;

    private PedidoDao pedDao;
    private ProductoDao prodDao;

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

        pedDao = BaseDatosRepository.getInstance(this).getPedidoDao();
        prodDao = BaseDatosRepository.getInstance(this).getProductoDao();


        Intent i = getIntent();
        if (((int) i.getExtras().get("VER_DETALLE")) == 1) {
            ///idPedidoMostrar = i.getExtras().getInt("ID_PEDIDO");
            buscarPedidoMostrar();
           /*repositorioPedido= new PedidoRepository();
            unPedido = repositorioPedido.buscarPorId(idPedidoMostrar);
            adaptadorLstProductoItem = new ArrayAdapter<DetallePedido>(NuevoPedidoActivity.this, android.R.layout.simple_list_item_single_choice, unPedido.getDetalle());
            lstPedidoItem.setAdapter(adaptadorLstProductoItem);
            edtPedidoCorreo.setText(unPedido.getMailContacto());
            edtPedidoCorreo.setEnabled(false);
            if (unPedido.getRetirar() == true) {
                optPedidoRetira.setActivated(true);
                optPedidoRetira.setEnabled(false);
                optPedidoEnvio.setActivated(false);
                optPedidoEnvio.setEnabled(false);
                edtPedidoDireccion.setEnabled(false);
            } else {
                optPedidoRetira.setActivated(false);
                optPedidoRetira.setEnabled(false);
                optPedidoEnvio.setActivated(true);
                optPedidoEnvio.setEnabled(false);
                edtPedidoDireccion.setText(unPedido.getDireccionEnvio());
                edtPedidoDireccion.setEnabled(false);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            edtHora.setText(sdf.format(unPedido.getFecha()));
            edtHora.setEnabled(false);
            btnAgregarProducto.setVisibility(View.INVISIBLE);
            btnHacerPedido.setVisibility(View.INVISIBLE);
            btnQuitarProducto.setVisibility(View.INVISIBLE);
            lblPedido.setText("Total del Pedido: $" + unPedido.total());*/
        } else {
            unPedido = new Pedido();
            //repositorioPedido = new PedidoRepository();
            //repositorioProducto = new ProductoRepository();
            adaptadorLstProductoItem = new ArrayAdapter<DetallePedido>(NuevoPedidoActivity.this, android.R.layout.simple_list_item_single_choice, unPedido.getDetalle());
            lstPedidoItem.setAdapter(adaptadorLstProductoItem);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NuevoPedidoActivity.this);
            edtPedidoCorreo.setText(preferences.getString("edtCorreoPreference", ""));
            if (!preferences.getBoolean("optRetirarPreference", true)) {
                optPedidoRetira.setChecked(false);
                optPedidoEnvio.setChecked(true);
                edtPedidoDireccion.setEnabled(true);
            }
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
                Intent i = new Intent(NuevoPedidoActivity.this, ListarProductoActivity.class);
                i.putExtra("NUEVO_PEDIDO", 1);
                startActivityForResult(i, 1);
            }
        });

        btnHacerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] horaIngresada = edtHora.getText().toString().split(":");
                final GregorianCalendar hora = new GregorianCalendar();
                final int valorHora = Integer.valueOf(horaIngresada[0]);
                final int valorMinutos = Integer.valueOf(horaIngresada[1]);
                if (edtPedidoCorreo.getText().toString().isEmpty()) {
                    Toast.makeText(NuevoPedidoActivity.this,
                            "Debe ingresar el correo", Toast.LENGTH_LONG).show();
                    return;
                }
                if (optPedidoEnvio.isChecked() && edtPedidoDireccion.getText().toString().isEmpty()) {
                    Toast.makeText(NuevoPedidoActivity.this,
                            "Debe ingresar la direccion", Toast.LENGTH_LONG).show();
                    return;
                }
                if (lstPedidoItem.getAdapter().getCount() == 0) {
                    Toast.makeText(NuevoPedidoActivity.this,
                            "Debe agregar al menos un producto", Toast.LENGTH_LONG).show();
                    return;
                }
                if (valorHora < 0 || valorHora > 23) {
                    Toast.makeText(NuevoPedidoActivity.this,
                            "La hora ingresada " + valorHora + " es incorrecta", Toast.LENGTH_LONG).show();
                    return;
                }
                if (valorMinutos < 0 || valorMinutos > 59) {
                    Toast.makeText(NuevoPedidoActivity.this,
                            "Los minutos " + valorMinutos + " son incorrectos", Toast.LENGTH_LONG).show();
                    return;
                }
                hora.set(Calendar.HOUR_OF_DAY, valorHora);
                hora.set(Calendar.MINUTE, valorMinutos);
                hora.set(Calendar.SECOND, Integer.valueOf(0));
                unPedido.setFecha(hora.getTime());
                unPedido.setMailContacto(edtPedidoCorreo.getText().toString());
                unPedido.setRetirar(optPedidoRetira.isChecked());
                unPedido.setDireccionEnvio(edtPedidoDireccion.getText().toString());
                unPedido.setEstado(Pedido.Estado.REALIZADO);
                //repositorioPedido.guardarPedido(unPedido);

                guardarPedido(unPedido);

                cambiarEstadoPedido();
                Intent i = new Intent(getApplicationContext(), HistorialPedidoActivity.class);
                startActivity(i);
            }

        });


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    void cambiarEstadoPedido() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                buscarPedidos();
              /*  Intent intent = new Intent();
                List<Pedido> lista = repositorioPedido.getLista();
                for (Pedido p : lista) {
                    if (p.getEstado().equals(Pedido.Estado.REALIZADO)) {
                        intent.putExtra("idPedido", p.getId());
                        intent.setAction("ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_ACEPTADO");
                        sendBroadcast(intent);
                    }
                }*/
            }
        };

        Thread unHilo = new Thread(r);
        unHilo.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int requestResult, @Nullable Intent data) {
        if (requestCode == 1 && requestResult == RESULT_OK) {
            int idProducto = data.getExtras().getInt("idProducto");
            // Producto item = repositorioProducto.buscarPorId(idProducto);
            int cantidad = data.getExtras().getInt("cantidad");
            buscarPorducto(idProducto, cantidad);
            /*DetallePedido detallePedido = new DetallePedido(cantidad, item);
            detallePedido.setPedido(unPedido);
            double precioTotal = unPedido.total();
            lblPedido.setText("Total del Pedido: $" + precioTotal);
            adaptadorLstProductoItem.notifyDataSetChanged();*/
        }
    }

    private void buscarPorducto(final Integer idProd, final Integer cant) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                final Producto item = prodDao.getProductoId(idProd);
                runOnUiThread(new Runnable() {
                    public void run() {
                        DetallePedido detallePedido = new DetallePedido(cant, item);
                        detallePedido.setPedido(unPedido);
                        double precioTotal = unPedido.total();
                        lblPedido.setText("Total del Pedido: $" + precioTotal);
                        adaptadorLstProductoItem.notifyDataSetChanged();
                    }
                });
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }

    private void buscarPedidoMostrar() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                List<Pedido> pedidoList=pedDao.getAll();
                unPedido = pedDao.getPedidoId(pedidoList.get(pedidoList.size()-1).getId());
                runOnUiThread(new Runnable() {
                    public void run() {
                        adaptadorLstProductoItem = new ArrayAdapter<DetallePedido>(NuevoPedidoActivity.this, android.R.layout.simple_list_item_single_choice, unPedido.getDetalle());
                        lstPedidoItem.setAdapter(adaptadorLstProductoItem);
                        edtPedidoCorreo.setText(unPedido.getMailContacto());
                        edtPedidoCorreo.setEnabled(false);
                        if (unPedido.getRetirar() == true) {
                            optPedidoRetira.setActivated(true);
                            optPedidoRetira.setEnabled(false);
                            optPedidoEnvio.setActivated(false);
                            optPedidoEnvio.setEnabled(false);
                            edtPedidoDireccion.setEnabled(false);
                        } else {
                            optPedidoRetira.setActivated(false);
                            optPedidoRetira.setEnabled(false);
                            optPedidoEnvio.setActivated(true);
                            optPedidoEnvio.setEnabled(false);
                            edtPedidoDireccion.setText(unPedido.getDireccionEnvio());
                            edtPedidoDireccion.setEnabled(false);
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        edtHora.setText(sdf.format(unPedido.getFecha()));
                        edtHora.setEnabled(false);
                        btnAgregarProducto.setVisibility(View.INVISIBLE);
                        btnHacerPedido.setVisibility(View.INVISIBLE);
                        btnQuitarProducto.setVisibility(View.INVISIBLE);
                        lblPedido.setText("Total del Pedido: $" + unPedido.total());
                    }
                });
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }

    private void guardarPedido(final Pedido pedido) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                pedDao.insert(pedido);
                final List<Pedido> ped = pedDao.getAll();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(NuevoPedidoActivity.this,
                                "El pedido fue guardado con exito", Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }

    private void buscarPedidos() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                final List<Pedido> lista = pedDao.getAll();
                Intent intent = new Intent();
                for (Pedido p : lista) {
                    if (p.getEstado().equals(Pedido.Estado.REALIZADO)) {
                        intent.putExtra("idPedido", p.getId());
                        intent.setAction("ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido.ESTADO_ACEPTADO");
                        sendBroadcast(intent);
                    }
                }
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }
}
