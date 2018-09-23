package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class ListaProducto extends AppCompatActivity {
    private Bundle datosMain;
    private Integer nuevo_pedido;
    private EditText edtProdCantidad;
    private Button btnProdAddPedido;
    private ProductoRepository repositorio_prod=new ProductoRepository();
    private Categoria categoriaSelc;
    private final List<Categoria> datosCategoria= repositorio_prod.getCategorias();
    private ArrayAdapter<Categoria> adaptadorSpinner;
    private Spinner cmbProductoCategoria;
    private ListView lstProductos;
    private ArrayAdapter<Producto> adaptadorLstProductos;
    private List<Producto> datosProductos;
    private Producto productoSelc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);
        edtProdCantidad=(EditText) findViewById(R.id.edtProdCantidad);
        btnProdAddPedido= (Button) findViewById(R.id.btnProdAddPedido);
        cmbProductoCategoria=(Spinner) findViewById(R.id.cmbProductosCategoria);
        lstProductos= (ListView) findViewById(R.id.lstProductos);
        adaptadorSpinner = new ArrayAdapter<Categoria>(ListaProducto.this,android.R.layout.simple_list_item_1,datosCategoria);
        categoriaSelc= datosCategoria.get(0);
        datosProductos=repositorio_prod.buscarPorCategoria(categoriaSelc);
        adaptadorLstProductos= new ArrayAdapter<Producto>(ListaProducto.this,android.R.layout.simple_list_item_single_choice, datosProductos);
        cmbProductoCategoria.setAdapter(adaptadorSpinner);
        lstProductos.setAdapter(adaptadorLstProductos);
        datosMain=this.getIntent().getExtras();
        nuevo_pedido=datosMain.getInt("NUEVO_PEDIDO");
        if(nuevo_pedido==0){
            edtProdCantidad.setEnabled(false);
            btnProdAddPedido.setEnabled(false);
        }
        cmbProductoCategoria.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    categoriaSelc= (Categoria) adapterView.getItemAtPosition(position);
                    datosProductos=repositorio_prod.buscarPorCategoria(categoriaSelc);
                    adaptadorLstProductos.clear();
                    adaptadorLstProductos.addAll(datosProductos);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                }
        );
        lstProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                productoSelc= (Producto)adapterView.getItemAtPosition(position);
            }
        });
        btnProdAddPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentResultado= new Intent();
                intentResultado.putExtra("cantidad",Integer.parseInt(edtProdCantidad.getText().toString()));
                intentResultado.putExtra("idProducto", productoSelc.getId());
                setResult(RESULT_OK,intentResultado);
                finish();
            }
        });
    }


}
