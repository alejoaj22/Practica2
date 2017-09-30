package com.example.alejo.practica2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        texto = (TextView) findViewById(R.id.nombre);

        ifoto = (ImageView) findViewById(R.id.foto);


        Bundle extras = getIntent().getExtras();
        correoR = extras.getString("correo");
        contraseñaR = extras.getString("contraseña");
        foto = extras.getString("foto");
        nombre = extras.getString("nombre");


        texto.setText("Correo: " + correoR + "\nNombre: " + nombre+ foto);





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

                //LoginManager.getInstance().logOut(); //Cierra sesión en facebook
                intent = new Intent(PerfilActivity.this,LoguinActivity.class);
                intent.putExtra("correomain", correoR);
                intent.putExtra("contraseñamain", contraseñaR);
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
        Glide.with(getApplicationContext()).load(foto)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ifoto);
    }
}
