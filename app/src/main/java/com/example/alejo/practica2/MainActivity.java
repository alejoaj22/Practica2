package com.example.alejo.practica2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class MainActivity extends AppCompatActivity {
    String correo="",contraseña="";
    String correoR, contraseñaR,nombreR;
    String fotoR="";
    int main=2222;

    int oplog;
    GoogleApiClient mGoogleApiClient;



    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        correoR = extras.getString("correo");
        //Log.d("correo del main",correoR);
        contraseñaR = extras.getString("contraseña");
        fotoR = extras.getString("foto");
        nombreR = extras.getString("nombre");
        //Log.d("correo del main",correoR);
        //Log.d("contraseña del main",contraseñaR);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"Error en login",Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();

        switch (id){
            case R.id.mMiPerfil:
                intent = new Intent(MainActivity.this,PerfilActivity.class);
                intent.putExtra("correo", correoR);
                intent.putExtra("contraseña", contraseñaR);
                intent.putExtra("foto",fotoR);
                intent.putExtra("nombre",nombreR);
                startActivity(intent);
                break;
            case R.id.mCerrar:

                prefs = getSharedPreferences("Mis preferencias",MODE_PRIVATE);
                editor = prefs.edit();

                editor.putInt("oplog",oplog);
                editor.commit();

                ///////////////////////////////cerrar sesión de google
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                            }
                        });
                ////////////////////////////////////////////////
                LoginManager.getInstance().logOut(); //Cierra sesión en facebook
                intent = new Intent(MainActivity.this,LoguinActivity.class);



                intent.putExtra("correomain", correoR);
                intent.putExtra("contraseñamain", contraseñaR);
                intent.putExtra("main", main);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
