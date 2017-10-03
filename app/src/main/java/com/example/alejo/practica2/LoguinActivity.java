package com.example.alejo.practica2;



import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Pattern;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
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

import org.json.JSONException;
import org.json.JSONObject;


public class LoguinActivity extends AppCompatActivity {
    String contraseñaR,correoR,nombreR,contraseñai,correoi,contraseñarr,correorr,correomain,contraseñamain;
    EditText eCorreo,eContraeña;
    String personPhotoUrl, personName ="";
    Uri urlphoto;
    int main;
    int mainR = 0;
    int oplong;



    String facebook_id;
    String f_name;
    String m_name;
    String l_name;
    String full_name;
    String profile_image;
    String email_id;
    String gender;
    String id_facebook;

    Bitmap imagen;





    SharedPreferences prefs;
    SharedPreferences.Editor editor;

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
            nombreR = extras.getString("nombremain");
        }


        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContraeña = (EditText) findViewById(R.id.eContraseña);


        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList(
                "email",
                "public_profile"
        ));












        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"Login Exitosooo",Toast.LENGTH_SHORT).show();

                //progress.show();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    facebook_id=profile.getId();
                    f_name=profile.getFirstName();
                    m_name=profile.getMiddleName();
                    l_name=profile.getLastName();
                    full_name=profile.getName();
                    profile_image=profile.getProfilePictureUri(400, 400).toString();
                    Toast.makeText(getApplicationContext(),full_name,Toast.LENGTH_SHORT).show();

                }


                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,GraphResponse response) {

                        JSONObject json = response.getJSONObject();
                        try {
                            if(json != null){
                                String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                                email_id = json.getString("email");
                                goMainActivity(1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,picture");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Login cancrelado",Toast.LENGTH_SHORT).show();
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
            nombreR = data.getExtras().getString("nombre");

            Log.d("correo",correoR);
            Log.d("contraseña",contraseñaR);
            Log.d("nombre",nombreR);
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
            prefs = getSharedPreferences("Mis preferencias",MODE_PRIVATE);
            oplong = 2;

            editor = prefs.edit();
            editor.putInt("oplong",oplong);
        }
        if (correoi.equals(correoR) && contraseñai.equals(contraseñaR)) {


            prefs = getSharedPreferences("Mis preferencias",MODE_PRIVATE);
            editor = prefs.edit();
            oplong = 1;
            editor.putInt("oplong",oplong);

            goMainActivity(2);

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

    public void goMainActivity(int option){
        // opcion 1 que es facebook extraer la foto y el correo
        // opción 2 solo mandar el correo y la contraseña
        // opcion 3 el correo con google
        Intent intent = new Intent(LoguinActivity.this, MainActivity.class);

        if(option == 1){
            //Log.d("correo del login",email_id);
            intent.putExtra("correo",email_id);

            intent.putExtra("nombre",full_name);
            intent.putExtra("foto",profile_image);
        }

        else if(option == 2){
            intent.putExtra("correo", correoR);
            intent.putExtra("contraseña", contraseñaR);
            intent.putExtra("nombre",nombreR);
        }
        else if(option == 3) {
            intent.putExtra("correo", correoR);
            intent.putExtra("contraseña", contraseñaR);
            intent.putExtra("foto", personPhotoUrl);
            intent.putExtra("nombre", personName);
        }
        prefs = getSharedPreferences("Mis preferencias",MODE_PRIVATE);
        editor = prefs.edit();
        editor.putInt("oplong",oplong);

        startActivity(intent);
        finish();

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

            personName = acct.getDisplayName();
            urlphoto = acct.getPhotoUrl();

            personPhotoUrl = urlphoto.toString().trim();
            correoR = acct.getEmail();

            goMainActivity(3);
            // ir a la actividad Main Activity
            // Ojo que el loguelo de google no va todavía
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    /////////////////////////////////////////////////
    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response",response.toString());

                            email_id = response.getJSONObject().getString("email");
                            full_name = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");



                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            profile_image = profile.getLinkUri().toString();
                            Log.i("Link",profile_image);
                            if (Profile.getCurrentProfile()!=null)
                            {
                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            }

                            Log.i("Login" + "Email", email_id);
                            Log.i("Login"+ "FirstName", full_name);
                            Log.i("Login" + "LastName", lastName);
                            Log.i("Login" + "Gender", gender);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

}
