package com.example.emrsupportapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emrsupportapp.R;
import com.example.emrsupportapp.activities.FaqTodo;
import com.example.emrsupportapp.activities.TrainingTodo;
import com.example.emrsupportapp.interfaces.TrainingOnClickListener;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingViewHolder> {
    Context context;
    List<TrainingTodo> titleList;
    TrainingOnClickListener trainingOnClickListener;

    public TrainingListAdapter(Context context, List<TrainingTodo> titleList, TrainingOnClickListener trainingOnClickListener) {
        this.context = context;
        this.titleList = titleList;
        this.trainingOnClickListener = trainingOnClickListener;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_cardview_traininglist, parent, false);
        return new TrainingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position) {
        holder.txtViewTrainingTitle.setText(titleList.get(position).getTitle());
    }

    public void filterList(List<TrainingTodo> filteredList) {
        titleList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public void refresh(List<TrainingTodo> titleList) {
        this.titleList.clear();
        this.titleList.addAll(titleList);
        notifyDataSetChanged();
    }

    public class TrainingViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTraining;
        TextView txtViewTrainingTitle;

        public TrainingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTraining = itemView.findViewById(R.id.ivTraining);
            txtViewTrainingTitle = itemView.findViewById(R.id.txtViewTrainingTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trainingOnClickListener.trainingOnClickListener(getAdapterPosition(), titleList.get(getAdapterPosition()));
                }
            });
        }
    }
}
