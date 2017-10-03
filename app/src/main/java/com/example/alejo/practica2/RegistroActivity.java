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
    String correo="",contraseña="",repcontraseña,Nombre;
    EditText eCorreo,eContraseña,eRepcontraseña,eNombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContraseña = (EditText) findViewById(R.id.eContraseña);
        eRepcontraseña = (EditText) findViewById(R.id.repcontraseña);
        eNombre = (EditText) findViewById(R.id.eNombre);


    }
    public void registrar(View view) {
        correo = eCorreo.getText().toString();
        contraseña = eContraseña.getText().toString();
        repcontraseña = eRepcontraseña.getText().toString();
        Nombre = eNombre.getText().toString();


        if ((validarEmail(correo)) && (contraseña.equals(repcontraseña)) && (!Nombre.isEmpty()) && (!contraseña.isEmpty())) {

            Intent intent = new Intent();
            intent.putExtra("correo", correo);
            intent.putExtra("contraseña", contraseña);
            intent.putExtra("nombre", Nombre);
            setResult(RESULT_OK, intent);
            finish();
        } else if (!validarEmail(correo)) {

            AlertDialog ventana = new AlertDialog.Builder(this).create();

            ventana.setTitle("Error:");
            ventana.setMessage("Email incorrecto");

            ventana.show();

        } else if (!contraseña.equals(repcontraseña)) {
            AlertDialog ventana = new AlertDialog.Builder(this).create();

            ventana.setMessage("Contraseñas no coinciden");
            ventana.setTitle("Error");
            ventana.show();

        } else if (Nombre.isEmpty()) {

            AlertDialog ventana = new AlertDialog.Builder(this).create();

            ventana.setMessage("Ingrese un nombre");
            ventana.setTitle("Error");
            ventana.show();
        } else if (contraseña.isEmpty()) {

            AlertDialog ventana = new AlertDialog.Builder(this).create();

            ventana.setMessage("Ingrese una contraseña");
            ventana.setTitle("Error");
            ventana.show();

    } else {
        AlertDialog ventana = new AlertDialog.Builder(this).create();

        ventana.setMessage("Error inesperado");
        ventana.setTitle("Error");
        ventana.show();}
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
