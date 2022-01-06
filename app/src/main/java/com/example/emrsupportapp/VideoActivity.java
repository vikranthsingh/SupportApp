package com.example.emrsupportapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URL;

public class VideoActivity extends AppCompatActivity {
    public VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.videoView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Uri uri = Uri.parse(bundle.getString("uri"));
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.requestFocus();
            videoView.seekTo(100);
            videoView.start();
        }
    }
}