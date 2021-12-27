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
import com.example.emrsupportapp.interfaces.RecyclerviewOnClickListener;


public class QueryListAdapter extends RecyclerView.Adapter<QueryListAdapter.MyViewHolder> {
    Context context;
    String[] queries;
    int[] images;
    RecyclerviewOnClickListener recyclerviewOnClickListener;

    public QueryListAdapter(Context context, String[] queries, int[] images, RecyclerviewOnClickListener recyclerviewOnClickListener) {
        this.context = context;
        this.queries = queries;
        this.images = images;
        this.recyclerviewOnClickListener = recyclerviewOnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.query_list_cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ivQuery.setImageResource(images[position]);
        holder.txtQuery.setText(queries[position]);
    }

    @Override
    public int getItemCount() {
        return queries.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivQuery;
        private TextView txtQuery;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivQuery = itemView.findViewById(R.id.ivQuery);
            txtQuery = itemView.findViewById(R.id.txtQuery);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerviewOnClickListener.onClickListener(getAdapterPosition());
                }
            });
        }
    }
}
