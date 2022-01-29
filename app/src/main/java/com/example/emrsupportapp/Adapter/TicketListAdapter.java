package com.example.emrsupportapp.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emrsupportapp.R;
import com.example.emrsupportapp.activities.TicketTodo;
import com.example.emrsupportapp.interfaces.TicketOnClickListener;

import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketViewHolder> {
    Context context;
    List<TicketTodo> titleList;
    TicketOnClickListener ticketOnClickListener;
    RecyclerOnClickListener mListener;

    public TicketListAdapter(Context context, List<TicketTodo> titleList, TicketOnClickListener ticketOnClickListener, RecyclerOnClickListener mListener) {
        this.context = context;
        this.titleList = titleList;
        this.ticketOnClickListener = ticketOnClickListener;
        this.mListener = mListener;
    }

    @NonNull
    @Override

    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_cardview_ticketlist, parent, false);
        return new TicketViewHolder(view, mListener);
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
        RecyclerOnClickListener mListener;

        public TicketViewHolder(@NonNull View itemView, RecyclerOnClickListener mListener) {
            super(itemView);
            this.mListener = mListener;
            txtViewTicketTitle = itemView.findViewById(R.id.txtViewTicketTitle);
            ivTicket = itemView.findViewById(R.id.ivTicket);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketOnClickListener.onClickListenerTicket(getAdapterPosition(), titleList.get(getAdapterPosition()));
                }
            });
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add("Select The Action");
                    menu.add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                            mListener.onClickItem(getAdapterPosition(), titleList.get(getAdapterPosition()));
                            return true;
                        }
                    });
                    /*menu.add("Move Ticket To FAQ").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(context, "Move Ticket To FAQ", Toast.LENGTH_SHORT).show();
                            mListener.onClickAddToFaq(getAdapterPosition());
                            return true;
                        }
                    });*/
                }
            });
            /*itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ticketOnClickListener.onLongClickListenerTicket(getAdapterPosition(), titleList.get(getAdapterPosition()));
                    return true;
                }
            });*/
        }

        /*@Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add("Select Option").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }*/
    }

    public interface RecyclerOnClickListener {
        void onClickItem(int position, TicketTodo ticketTodo);
        //void onClickAddToFaq(int position);
    }
}
