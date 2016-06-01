package com.appspot.data_base_1298.database.Activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.appspot.data_base_1298.database.Fragments.FragmentConocidos;
import com.appspot.data_base_1298.database.R;

/**
 * Created by rk521 on 1/05/16.
 */
public class ActivityConocidos extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_frame);

        final FragmentManager fm = getSupportFragmentManager();
        FragmentConocidos fragment = (FragmentConocidos) fm.findFragmentById(R.id.	frame);
        if (fragment == null) {
            fragment = new FragmentConocidos();
            fm.beginTransaction().add(R.id.frame, fragment).commit();
        }
    }
}
