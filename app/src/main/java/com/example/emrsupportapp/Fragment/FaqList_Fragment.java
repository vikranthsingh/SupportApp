package com.example.emrsupportapp.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.emrsupportapp.R;
import com.example.emrsupportapp.RecyclerviewOnClickListener;
import com.example.emrsupportapp.activities.DatabaseHelper;
import com.example.emrsupportapp.activities.FaqTodo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapter.FaqListAdapter;


public class FaqList_Fragment extends Fragment implements RecyclerviewOnClickListener {
    EditText etSearch;
    TextView txtFromDate, txtToDate;
    Button btnReset;
    FloatingActionButton addFloatingBtn;
    FaqListAdapter adapter;
    RecyclerView recyclerViewFaqList;
    List<FaqTodo> titleList;
    private static final String TAG = "TAG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq_list_, container, false);
        txtFromDate = view.findViewById(R.id.txtFromDate);
        txtToDate = view.findViewById(R.id.txtToDate);
        btnReset = view.findViewById(R.id.btnReset);
        etSearch = view.findViewById(R.id.etSearch);
        addFloatingBtn = view.findViewById(R.id.addFloatingBtn);
        recyclerViewFaqList = view.findViewById(R.id.recyclerViewFaqList);
        addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction().replace(R.id.container, new AddFaqFragment()).addToBackStack(null);
                transaction.commit();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFromDate.setText("");
                txtToDate.setText("");
                etSearch.setText("");
            }
        });
        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFromDatePicker();
            }
        });
        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToDatePicker();
            }
        });
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*names = new ArrayList<>();
        names.add("Provides a concise response quickly and effectively");
        names.add("Empowers the user to confidently use the site");
        names.add("Assists the completion of a purchase");
        names.add("Reassures a user about taking the next action");*/

        adapter = new FaqListAdapter(getActivity(), titleList, this);
        recyclerViewFaqList.setAdapter(adapter);
        recyclerViewFaqList.setHasFixedSize(true);
        recyclerViewFaqList.setLayoutManager(new LinearLayoutManager(getContext()));
        getAllTitleList();
        return view;
    }


    void onClickFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                CharSequence dateString = DateFormat.format("dd/MM/yyyy", calendar1);
                txtFromDate.setText(dateString);
            }
        }, YEAR, MONTH, DATE);
        dialog.show();
    }

    void onClickToDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                CharSequence dateString = DateFormat.format("dd/MM/yyyy", calendar1);
                txtToDate.setText(dateString);
            }
        }, YEAR, MONTH, DATE);
        dialog.show();
    }

    @Override
    public void onClickListener(int position) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.container, new FaqIndo_Fragment()).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onLongClickListener(int position) {
        titleList.remove(position);
        adapter.notifyDataSetChanged();
    }

    public void getAllTitleList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<FaqTodo> todoList = DatabaseHelper.getInstance(getActivity()).todoDao().getAllTileList();
                Log.i(TAG, "run: ");
            }
        });
        thread.start();
    }
}