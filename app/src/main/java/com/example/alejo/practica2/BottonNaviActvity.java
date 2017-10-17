package com.example.alejo.practica2;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class BottonNaviActvity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botton_navi_actvity);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        ft = fm.beginTransaction();
                        PerfilFragment fragment = new PerfilFragment();
                        ft.replace(R.id.frame,fragment).commit();
                        // TODO
                        return true;
                    case R.id.menu_search:
                        // TODO
                        return true;
                    case R.id.menu_notifications:
                        // TODO
                        return true;
                }
                return false;
            }
        });

    }


}
