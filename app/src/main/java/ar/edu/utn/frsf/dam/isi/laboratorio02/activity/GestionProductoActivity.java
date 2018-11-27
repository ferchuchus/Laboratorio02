package ar.edu.utn.frsf.dam.isi.laboratorio02.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.BaseDatosRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.CategoriaDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoDao;
import ar.edu.utn.frsf.dam.isi.laboratorio02.rest.RestClient;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRetrofit;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionProductoActivity extends AppCompatActivity {
    private Button btnMenu;
    private Button btnGuardar;
    private Spinner comboCategorias;
    private EditText nombreProducto;
    private EditText descProducto;
    private EditText precioProducto;
    private ToggleButton opcionNuevoBusqueda;
    private EditText idProductoBuscar;
    private Button btnBuscar;
    private Button btnBorrar;
    private Boolean flagActualizacion;
    private ArrayAdapter<Categoria> comboAdapter;
    private List<Categoria> datosCategoria;
    private Producto p = new Producto();
    private ProductoDao prodDao;
    private CategoriaDao catDao;

    //json-server --host 192.168.1.7 -p 5000 Documents/resto-db.json  ---> Tener el celu y la compu en la misma RED
    //http://192.168.1.7:5000/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_producto);

        flagActualizacion = false;
        opcionNuevoBusqueda = (ToggleButton) findViewById(R.id.abmProductoAltaNuevo);
        idProductoBuscar = (EditText) findViewById(R.id.abmProductoIdBuscar);
        nombreProducto = (EditText) findViewById(R.id.abmProductoNombre);
        descProducto = (EditText) findViewById(R.id.abmProductoDescripcion);
        precioProducto = (EditText) findViewById(R.id.abmProductoPrecio);
        comboCategorias = (Spinner) findViewById(R.id.abmProductoCategoria);
        btnMenu = (Button) findViewById(R.id.btnAbmProductoVolver);
        btnGuardar = (Button) findViewById(R.id.btnAbmProductoCrear);
        btnBuscar = (Button) findViewById(R.id.btnAbmProductoBuscar);
        btnBorrar = (Button) findViewById(R.id.btnAbmProductoBorrar);

        catDao = BaseDatosRepository.getInstance(this).getCategoriaDao();
        prodDao = BaseDatosRepository.getInstance(this).getProductoDao();

        opcionNuevoBusqueda.setChecked(false);
        btnBuscar.setEnabled(false);
        btnBorrar.setEnabled(false);
        idProductoBuscar.setEnabled(false);
        cargarCombo();

        opcionNuevoBusqueda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flagActualizacion = isChecked;
                btnBuscar.setEnabled(isChecked);
                btnBorrar.setEnabled(isChecked);
                idProductoBuscar.setEnabled(isChecked);
                nombreProducto.setText("");
                descProducto.setText("");
                precioProducto.setText("");
                idProductoBuscar.setText("");
                comboCategorias.setSelection(0);
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* p.setId(Integer.valueOf(idProductoBuscar.getText().toString()));
                ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);
                Call<Producto> altaCall = clienteRest.buscarProductoPorId(p.getId());
                altaCall.enqueue(new Callback<Producto>() {
                  @Override
                    public void onResponse(Call<Producto> call, Response<Producto> resp) {
                        switch (resp.code()) {
                            case 200:
                                p = resp.body();
                                nombreProducto.setText(p.getNombre());
                                descProducto.setText(p.getDescripcion());
                                precioProducto.setText(p.getPrecio().toString());
                               comboCategorias.setSelection(p.getCategoria().getId()-1);
                                break;
                            default:
                                Toast.makeText(GestionProductoActivity.this, "El Producto " + p.getId() + " no se encuentra!",
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {
                    }
                });*/
                p.setId(Integer.valueOf(idProductoBuscar.getText().toString()));
                if (p.getId() > 0)
                    buscarProductoID(p);
                else
                    Toast.makeText(GestionProductoActivity.this, "El ID debe ser mayor que 0",
                            Toast.LENGTH_LONG).show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  p.setNombre(nombreProducto.getText().toString());
                p.setDescripcion(descProducto.getText().toString());
                p.setPrecio(Double.valueOf( precioProducto.getText().toString()));
                p.setCategoria((Categoria) comboCategorias.getSelectedItem());
                if (camposLlenos(p)) {
                    if (!flagActualizacion) {//opcionNuevoBusqueda.isChecked()) {
                        ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);
                        p.setId(null);
                        Call<Producto> altaCall = clienteRest.crearProducto(p);
                        altaCall.enqueue(new Callback<Producto>() {
                            @Override
                            public void onResponse(Call<Producto> call, Response<Producto> resp) {
                                switch (resp.code()) {
                                    case 201:
                                        Toast.makeText(GestionProductoActivity.this, p.getNombre() + " creado con éxito =)",
                                                Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        Toast.makeText(GestionProductoActivity.this, "El Producto " + p.getNombre() + " no ha sido creado!",
                                                Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }

                            @Override
                            public void onFailure(Call<Producto> call, Throwable t) {
                            }
                        });
                    } else {
                        if (p.getId() != null) {
                            ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);
                            Call<Producto> altaCall = clienteRest.actualizarProducto(p.getId(), p);
                            altaCall.enqueue(new Callback<Producto>() {
                                @Override
                                public void onResponse(Call<Producto> call, Response<Producto> resp) {
                                    switch (resp.code()) {
                                        case 200:
                                            Toast.makeText(GestionProductoActivity.this, p.getNombre() + " actualizado con éxito =)",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(GestionProductoActivity.this, "El Producto " + p.getNombre() + " no ha sido actualizado!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Producto> call, Throwable t) {
                                }
                            });
                        } else {
                            Toast.makeText(GestionProductoActivity.this, "Primero debes buscar un producto para modificar!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } else
                    Toast.makeText(GestionProductoActivity.this, "Faltan rellenar campos!", Toast.LENGTH_LONG).show();
           */

                if (camposLlenos()) {
                    p.setNombre(nombreProducto.getText().toString());
                    p.setDescripcion(descProducto.getText().toString());
                    p.setPrecio(Double.valueOf(precioProducto.getText().toString()));
                    p.setCategoria((Categoria) comboCategorias.getSelectedItem());
                    if (!flagActualizacion) { //opcionNuevoBusqueda.isChecked())
                        guardarProducto(p);
                    } else if (p.getId() != null) {
                        actualizarProducto(p);
                    }
                    nombreProducto.setText("");
                    descProducto.setText("");
                    precioProducto.setText("");
                    idProductoBuscar.setText("");
                    comboCategorias.setSelection(0);
                    btnBorrar.setEnabled(false);
                } else
                    Toast.makeText(GestionProductoActivity.this, "Falta rellenar algunos campos",
                            Toast.LENGTH_LONG).show();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ProductoRetrofit clienteRest = RestClient.getInstance().getRetrofit().create(ProductoRetrofit.class);
                Call<Producto> altaCall = clienteRest.borrar(p.getId());
                altaCall.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call, Response<Producto> resp) {
                        switch (resp.code()) {
                            case 200:
                                Toast.makeText(GestionProductoActivity.this, p.getNombre() + " borrado con éxito =)",
                                        Toast.LENGTH_LONG).show();
                                nombreProducto.setText("");
                                descProducto.setText("");
                                precioProducto.setText("");
                                idProductoBuscar.setText("");
                                comboCategorias.setSelection(0);
                                break;
                            default:
                                Toast.makeText(GestionProductoActivity.this, "El Producto " + p.getNombre() + " no ha sido borrado!",
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {
                    }
                });*/
                borrarProducto(p);
                nombreProducto.setText("");
                descProducto.setText("");
                precioProducto.setText("");
                idProductoBuscar.setText("");
                comboCategorias.setSelection(0);
                btnBorrar.setEnabled(false);
                btnGuardar.setEnabled(false);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    private boolean camposLlenos(Producto p) {
        if (p.getNombre() != null && p.getCategoria() != null && p.getDescripcion() != null && p.getPrecio() != null)
            return true;
        return false;
    }

    private boolean camposLlenos() {
        if (nombreProducto.getText().toString() != null & descProducto.getText().toString() != null & precioProducto.getText().toString() != null)
            return true;
        return false;
    }

    private void cargarCombo() {

        Runnable r = new Runnable() {
            @Override
            public void run() {
           /*     CategoriaRest catRest = new CategoriaRest();
                try {
                    datosCategoria = catRest.listarTodas();
                } catch (IOException ie) {
                    ie.printStackTrace();
                } catch (JSONException je) {
                    je.printStackTrace();
                }
*/
                final List<Categoria> datosCategoria = catDao.getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        comboAdapter = new ArrayAdapter<Categoria>(GestionProductoActivity.this, android.R.layout.simple_list_item_1, datosCategoria);
                        comboCategorias.setAdapter(comboAdapter);

                    }
                });

            }
        };
        Thread combo = new Thread(r);
        combo.start();
    }

    private void guardarProducto(final Producto producto) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                prodDao.insert(producto);
                final List<Producto> productos = prodDao.getAll();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(GestionProductoActivity.this,
                                "El Producto " + productos.get(productos.size() - 1).getNombre() + " fue guardado con éxito", Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }

    private void actualizarProducto(final Producto producto) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                prodDao.update(producto);
                final Producto productoID = prodDao.getProductoId(producto.getId());
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(GestionProductoActivity.this,
                                "El Producto " + productoID.getNombre() + " fue actualizado con éxito. " + productoID.getPrecio() + "!!" + productoID.getDescripcion() + "!!" + productoID.getCategoria().getNombre(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }

    private void borrarProducto(final Producto producto) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                final Producto p = prodDao.getProductoId(producto.getId());
                prodDao.delete(p);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(GestionProductoActivity.this,
                                "El Producto " + p.getNombre() + " fue borrado con éxito. ", Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }

    private void buscarProductoID(final Producto producto) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                final Producto prodID = prodDao.getProductoId(producto.getId());
                Log.d("ME DASSSS","resltado: "+prodID);
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (prodID != null)
                            cargarCampos(prodID);
                        else
                            Toast.makeText(GestionProductoActivity.this, "No existe un producto con el ID: " + producto.getId(),
                                    Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        Thread hiloRest = new Thread(r);
        hiloRest.start();
    }

    private void cargarCampos(Producto producto) {
        nombreProducto.setText(producto.getNombre());
        descProducto.setText(producto.getDescripcion());
        precioProducto.setText(producto.getPrecio().toString());
        comboCategorias.setSelection(producto.getCategoria().getId() - 1);

    }
}