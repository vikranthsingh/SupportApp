package com.example.emrsupportapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emrsupportapp.R;
import com.example.emrsupportapp.activities.DatabaseHelper;
import com.example.emrsupportapp.activities.FaqTodo;

import java.util.ArrayList;
import java.util.List;


public class FaqInfo_Fragment extends Fragment {
    private final FaqTodo faqTodo;
    TextView titleFaqInfoText, titleDescFaqInfoText;
    ImageView ivImageInfo, ivVideoInfo;

    public FaqInfo_Fragment(FaqTodo faqTodo) {
        this.faqTodo = faqTodo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_faq_info, container, false);
        titleFaqInfoText = view.findViewById(R.id.titleFaqInfoText);
        titleDescFaqInfoText = view.findViewById(R.id.titleDescFaqInfoText);
        ivImageInfo = view.findViewById(R.id.ivImageInfo);
        ivVideoInfo = view.findViewById(R.id.ivVideoInfo);

        String title = faqTodo.getTitle();
        String desc = faqTodo.getDescription();
        titleFaqInfoText.setText(title);
        titleDescFaqInfoText.setText(desc);
        return view;
    }
}