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
import com.example.emrsupportapp.interfaces.TicketOnClickListener;

import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketViewHolder> {
    Context context;
    List<TicketTodo> titleList;
    TicketOnClickListener ticketOnClickListener;

    public TicketListAdapter(Context context, List<TicketTodo> titleList, TicketOnClickListener ticketOnClickListener) {
        this.context = context;
        this.titleList = titleList;
        this.ticketOnClickListener = ticketOnClickListener;
    }

    @NonNull
    @Override

    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_cardview_ticketlist, parent, false);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketOnClickListener.onClickListenerTicket(getAdapterPosition(), titleList.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ticketOnClickListener.onLongClickListenerTicket(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
