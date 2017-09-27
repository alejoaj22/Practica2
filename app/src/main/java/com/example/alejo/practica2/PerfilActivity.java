package com.example.alejo.practica2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PerfilActivity extends AppCompatActivity {
    String correoR="",contraseñaR="";
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        texto = (TextView) findViewById(R.id.texto);

        Bundle extras = getIntent().getExtras();
        correoR = extras.getString("correo");
        contraseñaR = extras.getString("contraseña");

        Log.d("correo del perfil",correoR);
        Log.d("contraseña del perfil",contraseñaR);
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
        finish();
    }
    public void iniciar(){
        Log.d("correo del perfil",correoR);
        Log.d("contraseña del perfil",contraseñaR);
        texto.setText(correoR);
    }
}
