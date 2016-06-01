package com.appspot.data_base_1298.database.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.appspot.data_base_1298.database.R;
import com.squareup.picasso.Picasso;

/**
 * Created by rk521 on 31/05/16.
 */
public class ActivityViewImage extends AppCompatActivity {
    Bundle data;
    String foto;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_image);
        img = (ImageView) findViewById(R.id.full_size_img);

        Intent intent =  getIntent();
        data = intent.getExtras();

        if (data != null) {
            this.foto = data.getString("foto");
        }

        if (!this.foto.isEmpty()) {
            Picasso.with(this).load(this.foto).into(img);
        }
    }
}
