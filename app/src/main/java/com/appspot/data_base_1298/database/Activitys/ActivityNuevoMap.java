package com.appspot.data_base_1298.database.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appspot.data_base_1298.database.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by rk521 on 30/05/16.
 */
public class ActivityNuevoMap extends AppCompatActivity implements OnMapReadyCallback {
    public static Double latitude;
    public static Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                setData(latLng.latitude, latLng.longitude);

                map.clear();
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                map.addMarker(markerOptions);
            }
        });

        LatLng place = new LatLng(19.4289712, -99.1372517);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 13));
    }

    private void cancelData() {
        this.latitude = 19.4289712;
        this.longitude = -99.1372517;
    }

    private void setData(Double lat, Double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapa_guardar_ubicacion:
                this.finish();
                return true;

            case R.id.mapa_cancelar_ubicacion:
                cancelData();
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
