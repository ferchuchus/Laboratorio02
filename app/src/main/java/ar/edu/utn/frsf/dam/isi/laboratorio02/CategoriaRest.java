package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
public class CategoriaRest {

    public void crearCategoria(Categoria c) throws JSONException, IOException {
        //Variables de conexion y stream de lectura
        HttpURLConnection urlConnection = null;
        DataOutputStream printOut = null;
        InputStream in = null;

        //Crear un objeto json que representa una categoria
        JSONObject categoriaJson = new JSONObject();
        categoriaJson.put("nombre", c.getNombre());

        //Abrir una conexion al servidor para enviar el post
        URL url = new URL("http://192.168.0.17:5000/categorias");
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setChunkedStreamingMode(0);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");

        //Obtener el ouputStream para escribir el JSON
        printOut = new DataOutputStream(urlConnection.getOutputStream());
        String str = categoriaJson.toString();
        byte[] jsonData = new byte[0];
        jsonData = str.getBytes("UTF-8");
        printOut.write(jsonData);
        printOut.flush();

        //Leer la respuesta
        in = new BufferedInputStream(urlConnection.getInputStream());
        InputStreamReader isw = new InputStreamReader(in);
        StringBuilder sb = new StringBuilder();
        int data = 0;
        data = isw.read();

        //Analizar el codigo de la respuesta
        if (urlConnection.getResponseCode() == 200 || urlConnection.getResponseCode() == 201) {
            while (data != -1) {
                  char curret = (char) data;
                  sb.append(curret);
                  data = isw.read();
                }
            }
        //capturar todas las excepciones
        //cerrar todos los streams y HTTPUrlConnection
        if (printOut != null) {
            try { printOut.close(); } catch (IOException e) { e.printStackTrace(); }}
            if (in != null) { try {in.close();} catch (IOException e){e.printStackTrace();}}
            if (urlConnection != null) urlConnection.disconnect();
        }
}
