package com.example.emrsupportapp.Fragment.FaqModule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emrsupportapp.ImageActivity;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.VideoActivity;
import com.example.emrsupportapp.activities.FaqTodo;


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
        titleDescFaqInfoText.setMovementMethod(new ScrollingMovementMethod());
        ivImageInfo = view.findViewById(R.id.ivImageInfo);
        ivVideoInfo = view.findViewById(R.id.ivVideoInfo);

        String title = faqTodo.getTitle();
        String desc = faqTodo.getDescription();
        String image = faqTodo.getImagesUrl();
        String video = faqTodo.getVideoUrl();

        titleFaqInfoText.setText(title);
        titleDescFaqInfoText.setText(desc);

        if (image != null) {
            ivImageInfo.setVisibility(View.VISIBLE);//ivImageInfo-> this one already initialzed so no chances of getting this null
            ivImageInfo.setImageURI(Uri.parse(image)); //image-> this variable is initialized with null so checking null safety for this
        }

        if (video != null) {
            ivVideoInfo.setVisibility(View.VISIBLE);
            ivVideoInfo.setImageBitmap(createVideoThumbnail(video));
        }

        ivImageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image != null){
                    Intent intent = new Intent(getActivity(), ImageActivity.class);
                    intent.putExtra("image", image);
                    getActivity().startActivity(intent);
                }
            }
        });
        ivVideoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra("video", video);
                startActivity(intent);
            }
        });

        return view;
    }

    public Bitmap createVideoThumbnail(String path) {
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
    }
}