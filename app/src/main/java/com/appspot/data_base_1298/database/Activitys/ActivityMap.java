package com.appspot.data_base_1298.database.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appspot.data_base_1298.database.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by rk521 on 29/05/16.
 */
public class ActivityMap extends AppCompatActivity implements OnMapReadyCallback {
    Bundle data;
    Double latitude;
    Double longitude;
    String name;
    String foto;
    Float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent =  getIntent();
        data = intent.getExtras();

        if (data != null) {
            this.latitude = data.getDouble("latitude");
            this.longitude = data.getDouble("longitude");
            this.name = data.getString("name");
            this.foto = data.getString("foto");
            this.rating = data.getFloat("rating");
        }
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng place = new LatLng(this.latitude, this.longitude);

        Marker marker_place = map.addMarker(new MarkerOptions().position(place).snippet("Calificacion: " + rating.toString() + " / 5").title(this.name));

        PicassoMarker marker = new PicassoMarker(marker_place);
        Picasso.with(this)
                .load(this.foto)
                .resize(100, 100)
                .centerCrop()
                .into(marker);

        marker_place.showInfoWindow();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 16));
    }


    public class PicassoMarker implements Target {
        Marker mMarker;

        PicassoMarker(Marker marker) {
            mMarker = marker;
        }

        @Override
        public int hashCode() {
            return mMarker.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof PicassoMarker) {
                Marker marker = ((PicassoMarker) o).mMarker;
                return mMarker.equals(marker);
            } else {
                return false;
            }
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }
}