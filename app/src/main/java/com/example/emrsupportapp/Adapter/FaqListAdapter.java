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
import com.example.emrsupportapp.interfaces.FaqOnClickListener;
import com.example.emrsupportapp.interfaces.RecyclerviewOnClickListener;

import java.util.List;

public class FaqListAdapter extends RecyclerView.Adapter<FaqListAdapter.MyViewHolder> {
    Context context;
    List<FaqTodo> titleList;
    FaqOnClickListener faqOnClickListener;

    public FaqListAdapter(Context context, List<FaqTodo> titleList, FaqOnClickListener faqOnClickListener) {
        this.context = context;
        this.titleList = titleList;
        this.faqOnClickListener = faqOnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_cardview_faqlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtViewFaqTitle.setText(titleList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public void refresh(List<FaqTodo> titleList) {
        this.titleList.clear();
        this.titleList.addAll(titleList);
        notifyDataSetChanged();
    }

    public void filterList(List<FaqTodo> filteredList) {
        titleList = filteredList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFaq;
        TextView txtViewFaqTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFaq = itemView.findViewById(R.id.ivFaq);
            txtViewFaqTitle = itemView.findViewById(R.id.txtViewFaqTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    faqOnClickListener.faqOnClickListener(getAdapterPosition(),titleList.get(getAdapterPosition()));
                }
            });

            /*itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerviewOnClickListener.onLongClickListener(getAdapterPosition());
                    return true;
                }
            });*/
        }
    }
}
