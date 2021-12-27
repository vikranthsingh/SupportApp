package com.example.emrsupportapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {
    private ImageView ivImageCaptured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ivImageCaptured = findViewById(R.id.ivImageCaptured);
        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        ivImageCaptured.setImageBitmap(bitmap);
    }
}