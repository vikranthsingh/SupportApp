package com.example.emrsupportapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.emrsupportapp.R;

public class InfoVideoActivity extends AppCompatActivity {
    VideoView videoViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_video);
        videoViewInfo = findViewById(R.id.videoViewInfo);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Uri uri = Uri.parse(bundle.getString("video"));
            videoViewInfo.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoViewInfo);
            videoViewInfo.setMediaController(mediaController);
            videoViewInfo.requestFocus();
            videoViewInfo.seekTo(100);
            videoViewInfo.start();
        }
    }
}