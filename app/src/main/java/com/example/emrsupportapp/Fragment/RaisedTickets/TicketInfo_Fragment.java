package com.example.emrsupportapp.Fragment.RaisedTickets;

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
import com.example.emrsupportapp.activities.TicketTodo;


public class TicketInfo_Fragment extends Fragment {
    private final TicketTodo ticketTodo;
    private TextView titleTicketInfoText, titleDescTicketInfoText, txtTicketStatusTitle, txtTicketStatusDesc;
    ImageView ivImageInfoTicket, ivVideoInfoTicket;

    public TicketInfo_Fragment(TicketTodo ticketTodo) {
        this.ticketTodo = ticketTodo;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_info, container, false);
        titleTicketInfoText = view.findViewById(R.id.titleTicketInfoText);
        titleDescTicketInfoText = view.findViewById(R.id.titleDescTicketInfoText);
        ivImageInfoTicket = view.findViewById(R.id.ivImageInfoTicket);
        ivVideoInfoTicket = view.findViewById(R.id.ivVideoInfoTicket);
        txtTicketStatusTitle = view.findViewById(R.id.txtTicketStatusTitle);
        txtTicketStatusDesc = view.findViewById(R.id.txtTicketStatusDesc);

        String title = ticketTodo.getTitle();
        String desc = ticketTodo.getDescription();
        String image = ticketTodo.getImagesUrl();
        String video = ticketTodo.getVideoUrl();
        String solutionDesc = ticketTodo.getTicketStatus();

        titleTicketInfoText.setText(title);
        titleDescTicketInfoText.setText(desc);

        if (image != null) {
            ivImageInfoTicket.setVisibility(View.VISIBLE);//ivImageInfo-> this one already initialzed so no chances of getting this null
            ivImageInfoTicket.setImageURI(Uri.parse(image)); //image-> this variable is initialized with null so checking null safety for this
        }

        if (video != null) {
            ivVideoInfoTicket.setVisibility(View.VISIBLE);
            ivVideoInfoTicket.setImageBitmap(createVideoThumbnail(video));
        }
        if (solutionDesc != null) {
            txtTicketStatusTitle.setVisibility(View.VISIBLE);
            txtTicketStatusDesc.setVisibility(View.VISIBLE);
            txtTicketStatusDesc.setText(solutionDesc);
        }
        ivVideoInfoTicket.setOnClickListener(new View.OnClickListener() {
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
        final Uri uri = Uri.parse(ticketTodo.getVideoUrl());
        Intent playIntent = new Intent(getActivity(), VideoActivity.class);
        playIntent.putExtra("uri", uri.toString());
        startActivity(playIntent);
    }
}