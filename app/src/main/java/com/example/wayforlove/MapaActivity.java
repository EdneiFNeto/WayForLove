package com.example.wayforlove;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.wayforlove.fragments.MapaFragments;

public class MapaActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView menu_toolbar;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        menu_toolbar = (ImageView) findViewById(R.id.menu_toolbar);
        menu_toolbar.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //fragments
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ts = manager.beginTransaction();
        ts.replace(R.id.frame_mapa, new MapaFragments());
        ts.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_toolbar:
                drawerLayout.openDrawer(Gravity.START);
                break;
        }
    }
}
