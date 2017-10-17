package com.example.alejo.practica2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;





public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;





    String correo="",contraseña="";
    String correoR, contraseñaR,nombreR;
    String fotoR="";
    int main=2222;

    int oplog;
    GoogleApiClient mGoogleApiClient;

    Toolbar appbar;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    FragmentManager fm;
    FragmentTransaction ft;



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

        prefs = getSharedPreferences("Mis preferencias",MODE_PRIVATE);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();









        String hola = prefs.getString("hola","");
       // Toast.makeText(getApplicationContext(),"Dure mas "+hola,Toast.LENGTH_LONG).show();



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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (id){
            case R.id.mMiPerfil:

                prefs = getSharedPreferences("Mis preferencias",MODE_PRIVATE);
                editor = prefs.edit();
                editor.putString("correo",correoR);
                editor.putString("contraseña",contraseñaR);
                editor.putString("foto",fotoR);
                editor.putString("nombre",nombreR);
                editor.commit();
                intent = new Intent(MainActivity.this,PerfilActivity.class);
                intent.putExtra("correo", correoR);
                intent.putExtra("contraseña", contraseñaR);
                intent.putExtra("foto",fotoR);
                intent.putExtra("nombre",nombreR);
                startActivity(intent);
                //PerfilFragment fragment = new PerfilFragment();
                //ft.replace(R.id.frame,fragment).commit();
                //ft.replace(R.id.frame,fragment);
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
                intent.putExtra("nombremain", nombreR);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void Cambio(View view) {
        Intent intent;
        intent = new Intent(MainActivity.this,NavigationActivity.class);
        startActivity(intent);
    }

    public void Cambiotap(View view) {
        Intent intent;
        intent = new Intent(MainActivity.this,TabeActivity.class);
        startActivity(intent);
    }

    public void CambioBotton(View view) {
        prefs = getSharedPreferences("Mis preferencias",MODE_PRIVATE);
        editor = prefs.edit();
        editor.putString("correo",correoR);
        editor.putString("contraseña",contraseñaR);
        editor.putString("foto",fotoR);
        editor.putString("nombre",nombreR);
        editor.commit();
        //intent = new Intent(MainActivity.this,PerfilActivity.class);
        //intent.putExtra("correo", correoR);
        //intent.putExtra("contraseña", contraseñaR);
        //intent.putExtra("foto",fotoR);
        //intent.putExtra("nombre",nombreR);
        //startActivity(intent);
        Intent intent;
        intent = new Intent(MainActivity.this,BottonNaviActvity.class);
        startActivity(intent);
    }
}
