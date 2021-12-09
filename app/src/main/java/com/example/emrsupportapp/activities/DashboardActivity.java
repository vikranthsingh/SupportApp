package com.example.emrsupportapp;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import Adapter.DashboardAdapter;

public class DashboardActivity extends MenuAppActivity implements RecyclerviewOnClickListener {
    RecyclerView recyclerViewDashboard;
    int[] dashboardImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerViewDashboard = findViewById(R.id.recyclerViewDashboard);

        dashboardImg = new int[]{R.drawable.vg, R.drawable.vt, R.drawable.scp};
        DashboardAdapter adapter = new DashboardAdapter(this, dashboardImg, this);
        recyclerViewDashboard.setAdapter(adapter);
        recyclerViewDashboard.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDashboard.setHasFixedSize(true);

    }

    @Override
    public void onClickListener(int position) {
        startActivity(new Intent(DashboardActivity.this, MainActivity.class));
    }

    @Override
    public void onLongClickListener(int position) {

    }
}