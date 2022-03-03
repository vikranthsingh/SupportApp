package com.example.emrsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class
ImageActivity extends AppCompatActivity {
    ImageView ivImageCaptured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ivImageCaptured = findViewById(R.id.ivImageCaptured);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String imageUrl = extras.getString("image");
        File imageFile = new File(imageUrl);
        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            ivImageCaptured.setImageBitmap(bitmap);
        }
    }
}