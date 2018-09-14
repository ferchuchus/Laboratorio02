package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class ListaProducto extends AppCompatActivity {
    final Categoria[] datos = new Categoria[]{};

    ArrayAdapter<Categoria> adaptador = new ArrayAdapter<Categoria>(getApplicationContext(),android.R.layout.simple_list_item_1,datos);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);
    }


}
