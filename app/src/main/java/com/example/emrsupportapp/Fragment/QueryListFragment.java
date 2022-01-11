package com.example.emrsupportapp.Fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.emrsupportapp.Adapter.QueryListAdapter;
import com.example.emrsupportapp.Fragment.FaqModule.FaqList_Fragment;
import com.example.emrsupportapp.Fragment.RaisedTickets.RaisedTicket_Fragment;
import com.example.emrsupportapp.Fragment.TrainingModule.TrainingList_Fragment;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.interfaces.RecyclerviewOnClickListener;


public class QueryListFragment extends Fragment implements RecyclerviewOnClickListener {
    RecyclerView recyclerviewQueryFragment;
    String[] queryList = {"Frequent asked Questions", "Training Module", "Raised Tickets List"};
    int[] queryImages = {R.drawable.questions, R.drawable.question, R.drawable.tickets};
    FaqList_Fragment fragment;
    TrainingList_Fragment trainingFragment;
    FragmentManager manager;
    RaisedTicket_Fragment raisedTicketFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query_list, container, false);
        recyclerviewQueryFragment = view.findViewById(R.id.recyclerviewQueryFragment);
        QueryListAdapter adapter = new QueryListAdapter(getContext(), queryList, queryImages, this);
        recyclerviewQueryFragment.setAdapter(adapter);
        recyclerviewQueryFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerviewQueryFragment.setHasFixedSize(true);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return view;
    }

    @Override
    public void onClickListener(int position) {
        switch (position) {
            case 0:
                Toast.makeText(getActivity(), "Frequent asked Questions", Toast.LENGTH_SHORT).show();
                fragment = new FaqList_Fragment();
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, new FaqList_Fragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                Toast.makeText(getActivity(), "Training Module", Toast.LENGTH_SHORT).show();
                trainingFragment = new TrainingList_Fragment();
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, new TrainingList_Fragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                Toast.makeText(getActivity(), "Raised Tickets List", Toast.LENGTH_SHORT).show();
                raisedTicketFragment = new RaisedTicket_Fragment();
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, new RaisedTicket_Fragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }

    }

    @Override
    public void onLongClickListener(int position) {
    }
}