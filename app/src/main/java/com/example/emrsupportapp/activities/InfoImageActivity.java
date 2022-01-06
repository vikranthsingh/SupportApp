package com.example.emrsupportapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.emrsupportapp.R;

public class InfoImageActivity extends AppCompatActivity {
    ImageView ivImageInfo;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_image);
        ivImageInfo = findViewById(R.id.ivImageInfo);
        Intent intent = getIntent();
        String setImage = intent.getStringExtra("image");
        ivImageInfo.setImageURI(Uri.parse(setImage));
    }
}