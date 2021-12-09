package com.example.emrsupportapp.Fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emrsupportapp.DashboardActivity;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.RecyclerviewOnClickListener;

import Adapter.QueryListAdapter;


public class QueryListFragment extends Fragment implements RecyclerviewOnClickListener {
    RecyclerView recyclerviewQueryFragment;
    String[] queryList = {"Frequent asked Questions", "Training Module", "Raised Tickets List"};
    int[] queryImages = {R.drawable.questions, R.drawable.question, R.drawable.tickets};
    FaqList_Fragment fragment;
    FragmentManager manager;

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
        fragment = new FaqList_Fragment();
        manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new FaqList_Fragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLongClickListener(int position) {
    }
}