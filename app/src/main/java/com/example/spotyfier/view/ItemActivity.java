package com.example.spotyfier.view;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.spotyfier.R;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Bundle parametros = this.getIntent().getExtras();

        String fullname = parametros.getString("fullName");
        String title = parametros.getString("title");
        String family = parametros.getString("family");
        String url = parametros.getString("url");

        TextView txtFullName;
        TextView txtTitle;
        TextView txtFamily;
        ImageView txtUrl;

        txtFullName = (TextView) findViewById(R.id.itemFullName);
        txtTitle = (TextView) findViewById(R.id.itemTitle);
        txtFamily = (TextView) findViewById(R.id.itemFamily);
        txtUrl = (ImageView) findViewById(R.id.itemUrl);

        txtFullName.setText(fullname);
        txtTitle.setText(title);
        txtFamily.setText(family);
        txtUrl.setImageURI(Uri.parse(url));
        Glide.with(txtUrl) //Glide nos ayuda a cargar la imagen del item
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(txtUrl);
    }
}