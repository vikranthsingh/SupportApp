package com.example.emrsupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class
ImageActivity extends AppCompatActivity {
    ImageView ivImageCaptured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ivImageCaptured = findViewById(R.id.ivImageCaptured);

    }
}