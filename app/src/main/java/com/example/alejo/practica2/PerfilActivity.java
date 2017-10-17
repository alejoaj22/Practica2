package com.example.alejo.practica2;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;

public class PerfilActivity extends AppCompatActivity {
    String correoR="",contraseñaR="", foto="",nombre="";
    TextView texto;
    ImageView ifoto;
    GoogleApiClient mGoogleApiClient;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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



        setContentView(R.layout.activity_perfil);

        texto = (TextView) findViewById(R.id.nombre);

        ifoto = (ImageView) findViewById(R.id.foto);


        Bundle extras = getIntent().getExtras();
        correoR = extras.getString("correo");
        contraseñaR = extras.getString("contraseña");
        foto = extras.getString("foto");
        nombre = extras.getString("nombre");


        texto.setText("Correo: " + correoR + "\nNombre: " + nombre);





        //Log.d("correo del perfil",correoR);
        //Log.d("contraseña del perfil",contraseñaR);
        iniciar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();

        switch (id){
            case R.id.mPrincipal:
                finish();
                break;
            case R.id.mCerrar:

                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if(accessToken != null){
                    LoginManager.getInstance().logOut();

                }else if(mGoogleApiClient.isConnected()){

                    ///////////////////////////////cerrar sesión de google//////////////////////////////////////////////////////////
                    //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    //mGoogleApiClient.connect();
                    signOut();
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                }
                //LoginManager.getInstance().logOut(); //Cierra sesión en facebook
                intent = new Intent(PerfilActivity.this,LoguinActivity.class);
                intent.putExtra("correomain", correoR);
                intent.putExtra("contraseñamain", contraseñaR);
                intent.putExtra("nombremain", nombre);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Salir(View view) {
        Intent intent = new Intent(PerfilActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void iniciar(){
        //Log.d("correo del perfil",correoR);
        //Log.d("contraseña del perfil",contraseñaR);
        //texto.setText(correoR);
       if(foto != null) Glide.with(getApplicationContext()).load(foto)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ifoto);
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }
}
