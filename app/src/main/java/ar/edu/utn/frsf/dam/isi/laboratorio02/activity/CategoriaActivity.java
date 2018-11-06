package ar.edu.utn.frsf.dam.isi.laboratorio02.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import ar.edu.utn.frsf.dam.isi.laboratorio02.rest.CategoriaRest;
import ar.edu.utn.frsf.dam.isi.laboratorio02.R;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {
    private static int ID=0;
    private EditText textoCat;
    private Button btnCrear;
    private Button btnMenu;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        textoCat= (EditText) findViewById(R.id.txtNombreCategoria);
        handler= new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message message){
                if(message.what==1){
                    if(message.arg1==0){ Toast.makeText(CategoriaActivity.this,
                            "La categoria fue guardad con exito", Toast.LENGTH_LONG).show();
                            textoCat.getText().clear();
                    }
                    else Toast.makeText(CategoriaActivity.this,
                            "No se pudo guardar la categoria", Toast.LENGTH_LONG).show();
                }
            }
        };
        btnCrear= (Button) findViewById(R.id.btnCrearCategoria);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriaRest categoriaRest= new CategoriaRest();
                Categoria categoria= new Categoria();
                categoria.setId(ID ++);
                categoria.setNombre(textoCat.getText().toString());
                guardarCategoria(categoriaRest, categoria);

            }
        });
        btnMenu= (Button) findViewById(R.id.btnCategoriaVolver);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(CategoriaActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void guardarCategoria(final CategoriaRest c, final Categoria categoria){
        final Message mensaje= new Message();
        Runnable r= new Runnable() {
            @Override
            public void run() {
                int error=0;
                try{c.crearCategoria(categoria);}
                catch (JSONException eJson){eJson.printStackTrace(); error=1;}
                catch (IOException e) {e.printStackTrace(); error=1;}
                mensaje.what=1;
                mensaje.arg1=error;
                handler.sendMessage(mensaje);
            }
        };
        Thread hiloRest= new Thread(r);
        hiloRest.start();
    }
}
