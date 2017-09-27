package com.example.alejo.practica2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {
    String correo="",contraseña="",repcontraseña;
    EditText eCorreo,eContraseña,eRepcontraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContraseña = (EditText) findViewById(R.id.eContraseña);
        eRepcontraseña = (EditText) findViewById(R.id.repcontraseña);


    }
    public void registrar(View view){
        correo = eCorreo.getText().toString();
        contraseña = eContraseña.getText().toString();
        repcontraseña = eRepcontraseña.getText().toString();


        if( (validarEmail(correo)) && (contraseña.equals(repcontraseña)) ) {

            Intent intent = new Intent();
            intent.putExtra("correo", correo);
            intent.putExtra("contraseña", contraseña);
            setResult(RESULT_OK, intent);
            finish();
        }else if(!validarEmail(correo)){

            AlertDialog ventana = new AlertDialog.Builder(this).create();

            ventana.setTitle("Error:");
            ventana.setMessage("Email incorrecto");

            ventana.show();

        }
        else if(!contraseña.equals(repcontraseña)){
            AlertDialog ventana = new AlertDialog.Builder(this).create();

            ventana.setMessage("Contraseñas no coinciden");
            ventana.setTitle("Error");
            ventana.show();

        }else{

            AlertDialog ventana = new AlertDialog.Builder(this).create();

            ventana.setMessage("LAs contraseñas no coinciden");
            ventana.setTitle("Error");
            ventana.show();
        }

    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
