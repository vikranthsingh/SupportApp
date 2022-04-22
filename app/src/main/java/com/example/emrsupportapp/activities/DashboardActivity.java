package com.example.emrsupportapp.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.emrsupportapp.Adapter.DashboardAdapter;
import com.example.emrsupportapp.Fragment.QueryListFragment;
import com.example.emrsupportapp.Fragment.RaisedTickets.AddTicket_Fragment;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.activities.MainActivity;
import com.example.emrsupportapp.constants.Constants;
import com.example.emrsupportapp.enums.ModuleType;
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
//From here passing the module type to main activity
    @Override
    public void onClickListener(int position) {
        switch (position) {
            case Constants.ID_VG:
                Intent vgIntent = new Intent(DashboardActivity.this, MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("moduleType", ModuleType.VG.toString());
                vgIntent.putExtras(extras);
                Toast.makeText(DashboardActivity.this, ModuleType.VG.toString(), Toast.LENGTH_SHORT).show();
                startActivity(vgIntent);
                break;
            case Constants.ID_VT:
                Intent vtIntent = new Intent(DashboardActivity.this, MainActivity.class);
                Bundle vtExtras = new Bundle();
                vtExtras.putString("moduleType", ModuleType.VT.toString());
                vtIntent.putExtras(vtExtras);
                Toast.makeText(DashboardActivity.this, ModuleType.VT.toString(), Toast.LENGTH_SHORT).show();
                startActivity(vtIntent);
                break;
            case Constants.ID_CSP:
                Intent cspIntent = new Intent(DashboardActivity.this, MainActivity.class);
                Bundle cspExtras = new Bundle();
                cspExtras.putString("moduleType", ModuleType.CSP.toString());
                cspIntent.putExtras(cspExtras);
                Toast.makeText(DashboardActivity.this, ModuleType.CSP.toString(), Toast.LENGTH_SHORT).show();
                startActivity(cspIntent);
                break;
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}