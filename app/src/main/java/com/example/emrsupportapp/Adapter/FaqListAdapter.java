package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emrsupportapp.R;
import com.example.emrsupportapp.RecyclerviewOnClickListener;
import com.example.emrsupportapp.activities.FaqTodo;

import java.util.List;

public class FaqListAdapter extends RecyclerView.Adapter<FaqListAdapter.MyViewHolder> {
    Context context;
    List<FaqTodo> titleList;
    RecyclerviewOnClickListener recyclerviewOnClickListener;

    public FaqListAdapter(Context context, List<FaqTodo> titleList, RecyclerviewOnClickListener recyclerviewOnClickListener) {
        this.context = context;
        this.titleList = titleList;
        this.recyclerviewOnClickListener = recyclerviewOnClickListener;
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
        holder.txtViewFaqTitle.setText((CharSequence) titleList.get(position));
    }

    @Override
    public int getItemCount() {
        return titleList.size();
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
                    recyclerviewOnClickListener.onClickListener(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerviewOnClickListener.onLongClickListener(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
