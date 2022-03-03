package com.example.emrsupportapp.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.emrsupportapp.Adapter.DashboardAdapter;
import com.example.emrsupportapp.Fragment.RaisedTickets.AddTicket_Fragment;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.activities.MainActivity;
import com.example.emrsupportapp.interfaces.RecyclerviewOnClickListener;


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
        switch (position) {
            case 0:
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                Bundle bundle = new Bundle();
                bundle.putString("key", "VG");
                //AddTicket_Fragment addTicket_fragment = new AddTicket_Fragment();
                //addTicket_fragment.setArguments(bundle);
                Toast.makeText(DashboardActivity.this, "Vision Guardian", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                Toast.makeText(DashboardActivity.this, "Vision Technician", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                Toast.makeText(DashboardActivity.this, "Community Screening Program", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}