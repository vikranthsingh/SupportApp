package com.example.emrsupportapp.Fragment.TrainingModule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emrsupportapp.R;
import com.example.emrsupportapp.VideoActivity;
import com.example.emrsupportapp.activities.TrainingTodo;


public class TrainingInfo_Fragment extends Fragment {
    TextView titleInfoTextTraining, titleDescInfoTextTraining;
    ImageView ivImageInfoTraining, ivVideoInfoTraining;
    private final TrainingTodo trainingTodo;

    public TrainingInfo_Fragment(TrainingTodo trainingTodo) {
        this.trainingTodo = trainingTodo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training_info, container, false);
        titleInfoTextTraining = view.findViewById(R.id.titleInfoTextTraining);
        titleDescInfoTextTraining = view.findViewById(R.id.titleDescInfoTextTraining);
        ivImageInfoTraining = view.findViewById(R.id.ivImageInfoTraining);
        ivVideoInfoTraining = view.findViewById(R.id.ivVideoInfoTraining);

        String title = trainingTodo.getTitle();
        String desc = trainingTodo.getDescription();
        String image = trainingTodo.getImagesUrl();
        String video = trainingTodo.getVideoUrl();

        titleInfoTextTraining.setText(title);
        titleDescInfoTextTraining.setText(desc);

        if (image != null) {
            ivImageInfoTraining.setVisibility(View.VISIBLE);
            ivImageInfoTraining.setImageURI(Uri.parse(image));
        }

        if (video != null) {
            ivVideoInfoTraining.setVisibility(View.VISIBLE);
            ivVideoInfoTraining.setImageBitmap(createVideoThumbnail(video));
        }
        ivVideoInfoTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToVideoDisplayActivity();
            }
        });
        return view;
    }

    public Bitmap createVideoThumbnail(String path) {
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
    }

    private void moveToVideoDisplayActivity() {
        final Uri uri = Uri.parse(trainingTodo.getVideoUrl());
        Intent playIntent = new Intent(getActivity(), VideoActivity.class);
        playIntent.putExtra("uri", uri.toString());
        startActivity(playIntent);
    }
}