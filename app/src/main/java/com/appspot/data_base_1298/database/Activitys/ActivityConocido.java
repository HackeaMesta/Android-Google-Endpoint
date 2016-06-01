package com.appspot.data_base_1298.database.Activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.appspot.data_base_1298.database.Fragments.FragmentConocido;
import com.appspot.data_base_1298.database.R;

import static com.appspot.data_base_1298.database.Tools.Constantes.EXTRA_ID;
public class ActivityConocido extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_frame);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        final FragmentManager fm = getSupportFragmentManager();
        FragmentConocido fragment =
                (FragmentConocido) fm.findFragmentById(R.id.frame);
        if (fragment == null) {
            fragment = new FragmentConocido();
            fragment.setIdViewModel(getIntent().getStringExtra(EXTRA_ID));
            fm.beginTransaction().add(R.id.frame, fragment).commit();
        }
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

