package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

@SuppressWarnings("deprecation")
public class RestoFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token= FirebaseInstanceId.getInstance().getToken();
        Log.d("NOTICIAS", "token: "+token);
    }

    private void guardarToken(String _token){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("registration_id", _token);
        editor.apply();
    }

    private String leerToken(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }
}
