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
import com.example.emrsupportapp.activities.TicketTodo;

import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketViewHolder> {
    Context context;
    List<TicketTodo> titleList;

    public TicketListAdapter(Context context, List<TicketTodo> titleList) {
        this.context = context;
        this.titleList = titleList;
    }

    @NonNull
    @Override

    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_ticket_list, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        holder.txtViewTicketTitle.setText(titleList.get(position).getTitle());
    }

    public void refresh(List<TicketTodo> titleList) {
        this.titleList.clear();
        this.titleList.addAll(titleList);
        notifyDataSetChanged();
    }

    public void filterList(List<TicketTodo> filteredList) {
        titleList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewTicketTitle;
        ImageView ivTicket;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTicketTitle = itemView.findViewById(R.id.txtViewTicketTitle);
            ivTicket = itemView.findViewById(R.id.ivTicket);
        }
    }
}
