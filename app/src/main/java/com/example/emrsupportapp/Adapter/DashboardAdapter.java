package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emrsupportapp.R;
import com.example.emrsupportapp.RecyclerviewOnClickListener;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {
    Context context;
    int[] imageList;
    RecyclerviewOnClickListener recyclerviewOnClickListener;

    public DashboardAdapter(Context context, int[] imageList, RecyclerviewOnClickListener recyclerviewOnClickListener) {
        this.context = context;
        this.imageList = imageList;
        this.recyclerviewOnClickListener = recyclerviewOnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dashboard_cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageViewDashboard.setImageResource(imageList[position]);
    }

    @Override
    public int getItemCount() {
        return imageList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewDashboard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewDashboard = itemView.findViewById(R.id.imageViewDashboard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerviewOnClickListener.onClickListener(getAdapterPosition());
                }
            });
        }
    }
}
