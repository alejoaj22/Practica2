package com.example.alejo.practica2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment  {


    String correoR="",contraseñaR="", foto="",nombre="";
    TextView texto;
    ImageView ifoto;
    GoogleApiClient mGoogleApiClient;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;



    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);









        texto = (TextView) view.findViewById(R.id.nombre);
        ifoto = (ImageView) view.findViewById(R.id.foto);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        Context context;

        // cambie this por getActivity() para que dejara de mandar error por estar trabajando en un framento
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage( getActivity() /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //Toast.makeText(getApplicationContext(),"Error en login",Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        prefs = getActivity().getSharedPreferences("Mis preferencias",Context.MODE_PRIVATE);

        correoR = prefs.getString("correo","");
        contraseñaR = prefs.getString("contraseña","");
        nombre = prefs.getString("nombre","");
        foto = prefs.getString("foto","");


        texto.setText("Correo: " + correoR + "\nNombre: " + nombre);


        iniciar();


        return view;
    }

    public void iniciar(){
        //Log.d("correo del perfil",correoR);
        //Log.d("contraseña del perfil",contraseñaR);
        //texto.setText(correoR);

        if(foto != null) Glide.with(getActivity().getApplicationContext()).load(foto)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ifoto);
    }

}
