package com.example.emrsupportapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.emrsupportapp.Fragment.QueryListFragment;
import com.example.emrsupportapp.R;


public class MainActivity extends MenuAppActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String moduleType = extras.getString("moduleType");
        /*String moduleVG = extras.getString("VG");
        String moduleVT = extras.getString("VT");
        String moduleCSP = extras.getString("CSP");
        Log.i(TAG, "onCreate: " + moduleVG + " " + moduleVT + " " + moduleCSP);*/


        QueryListFragment queryListFragment = new QueryListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("moduleType", moduleType);
        queryListFragment.setArguments(bundle);
        /*if (moduleVG!=null && moduleVG.equals("VG")) {
            bundle.putString("VG", moduleVG);
            queryListFragment.setArguments(bundle);
        } else if (moduleVT!=null && moduleVT.equals("VT")) {
            bundle.putString("VT", moduleVT);
            queryListFragment.setArguments(bundle);
        } else if (moduleCSP!=null && moduleCSP.equals("CSP")) {
            bundle.putString("CSP", moduleCSP);
            queryListFragment.setArguments(bundle);
        }*/
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, queryListFragment);
        transaction.commit();
    }
}