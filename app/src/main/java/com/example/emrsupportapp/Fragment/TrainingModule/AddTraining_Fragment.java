package com.example.emrsupportapp.Fragment.TrainingModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.emrsupportapp.R;


public class AddTraining_Fragment extends Fragment {
    EditText etTrainingTitle, etTrainingDescription;
    ImageView ivImageCaptureTraining, ivVideoCaptureTraining;
    Button btnSubmitTraining;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_training, container, false);
        etTrainingTitle = view.findViewById(R.id.etTrainingTitle);
        etTrainingDescription = view.findViewById(R.id.etTrainingDescription);
        ivImageCaptureTraining = view.findViewById(R.id.ivImageCaptureTraining);
        ivVideoCaptureTraining = view.findViewById(R.id.ivVideoCaptureTraining);
        btnSubmitTraining = view.findViewById(R.id.btnSubmitTraining);
        btnSubmitTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trainingTitle = etTrainingTitle.getText().toString();
                String trainingDesc = etTrainingDescription.getText().toString();
            }
        });

        return view;
    }
}