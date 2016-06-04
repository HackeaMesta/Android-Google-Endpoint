package com.appspot.data_base_1298.database.Activitys;

/**
 * Created by rk521 on 1/05/16.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.appspot.data_base_1298.database.Fragments.FragmentNuevoConocido;
import com.appspot.data_base_1298.database.R;

public class ActivityNuevoConocido extends AppCompatActivity {
    Bundle data;
    public static Double latitude;
    public static Double longitude;
    public static String name;
    public static String foto;
    public static Float rating;
    public static String id;
    public static String telefono;
    public static String descripcion;
    public static String web;
    public static Boolean wifi;
    public static Boolean action_editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_frame);

        Intent intent =  getIntent();
        data = intent.getExtras();
        this.action_editar = false;

        if (data != null) {
            this.latitude = data.getDouble("latitude");
            this.longitude = data.getDouble("longitude");
            this.name = data.getString("name");
            this.foto = data.getString("foto");
            this.rating = data.getFloat("rating");
            this.id = data.getString("id");
            this.telefono = data.getString("telefono");
            this.descripcion = data.getString("descripcion");
            this.web = data.getString("web");
            this.wifi = data.getBoolean("wifi");
            this.action_editar = true;
        }

        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        final FragmentManager fm = getSupportFragmentManager();
        FragmentNuevoConocido fragment = (FragmentNuevoConocido) fm.findFragmentById(R.id.frame);
        if (fragment == null) {
            fragment = new FragmentNuevoConocido();
            fm.beginTransaction().add(R.id.frame, fragment).commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}