package com.example.alejo.practica2;



import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Pattern;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;


public class LoguinActivity extends AppCompatActivity {
    String contraseñaR,correoR,contraseñai,correoi,contraseñarr,correorr,correomain,contraseñamain;
    EditText eCorreo,eContraeña;
    String mainr;
    int main;
    int mainR = 0;


    LoginButton loginButton;
    CallbackManager callbackManager;
    private int option; // 1:facebook,2:contraseña,3:google
    GoogleApiClient mGoogleApiClient;


    @Override
    /// podemso tulizar los metodos onresume(), onpuuse, onstop, ondestroy
    // y elonrestart

    //metodo del boton onBackpresset(), es el del boton atras del celular
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);





        ////////Logueo google
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


        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                signIn();

                                            }
        });
                ///////////////////////////////////////////////////////////////////////////////
                Bundle extras = getIntent().getExtras();
        if(extras!=null){
            correoR = extras.getString("correomain");
            contraseñaR = extras.getString("contraseñamain");
        }


        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContraeña = (EditText) findViewById(R.id.eContraseña);


        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList(
                "email"
        ));

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"Login Exitosooo",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Login cancelado",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Error en el login",Toast.LENGTH_SHORT).show();
            }
        });

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.alejo.practica2",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
    public void registrarse(View view){
        Intent intent = new Intent(LoguinActivity.this,RegistroActivity.class);
        startActivityForResult(intent,1234);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1234 && resultCode == RESULT_OK){
            correoR = data.getExtras().getString("correo");
            contraseñaR = data.getExtras().getString("contraseña");

            Log.d("correo",correoR);
            Log.d("contraseña",contraseñaR);
        }else if (requestCode == 5678){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


        else{
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void iniciar(View view) {
        //validaciones

        correoi = eCorreo.getText().toString();
        contraseñai = eContraeña.getText().toString();
        if(mainR == 2222){
            Log.d("correo del 2222",correoR);
            Log.d("contraseña del 2222",contraseñaR);
        }
        if (correoi.equals(correoR) && contraseñai.equals(contraseñaR)) {
            Intent intent = new Intent(LoguinActivity.this, MainActivity.class);
            intent.putExtra("correo", correoR);
            intent.putExtra("contraseña", contraseñaR);
            startActivity(intent);
            finish();
        }
        else{
            AlertDialog ventana = new AlertDialog.Builder(this).create();
            ventana.setMessage("Error en los datos ingresados");
            ventana.setTitle("Error");
            ventana.show();
        }


    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void getMainActivity(){
        // opcion 1 que es facebook extraer la foto y el correo
        // opción 2 solo mandar el correo y la contraseña
        // opcion 3 el correo con google
    }

    // metodo de google

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 5678);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Gooogle", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(getApplicationContext(),acct.getDisplayName(),Toast.LENGTH_SHORT).show();
            ///goMainActivity();
            // ir a la actividad Main Activity
            // Ojo que el loguelo de google no va todavía
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
        } else {
            // Signed out, show unauthenticated UI.

        }
    }
}
