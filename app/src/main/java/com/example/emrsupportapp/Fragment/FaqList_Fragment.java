package com.example.emrsupportapp.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.example.emrsupportapp.Adapter.FaqListAdapter;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.activities.DatabaseHelper;
import com.example.emrsupportapp.activities.FaqTodo;
import com.example.emrsupportapp.interfaces.FaqOnClickListener;
import com.example.emrsupportapp.interfaces.RecyclerviewOnClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FaqList_Fragment extends Fragment implements FaqOnClickListener {
    EditText etSearch;
    TextView txtFromDate, txtToDate;
    Button btnReset, btnGo;
    FloatingActionButton addFloatingBtn;
    FaqListAdapter adapter;
    RecyclerView recyclerViewFaqList;
    List<FaqTodo> titleList = new ArrayList<>();
    List<FaqTodo> dateList = new ArrayList<>();
    private static final String TAG = "TAG";
    FaqTodo faqTodo;
    Calendar setCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq_list_, container, false);
        txtFromDate = view.findViewById(R.id.txtFromDate);
        txtToDate = view.findViewById(R.id.txtToDate);
        btnReset = view.findViewById(R.id.btnReset);
        btnGo = view.findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDate = txtFromDate.getText().toString();
                String toDate = txtToDate.getText().toString();
                getDateList(fromDate, toDate);
                Toast.makeText(getActivity(), "FromDate : " + fromDate + "\nToDate : " + toDate, Toast.LENGTH_SHORT).show();
            }
        });
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
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

        /*titleList = new ArrayList<>();
        titleList.add("Provides a concise response quickly and effectively");
        titleList.add("Empowers the user to confidently use the site");
        titleList.add("Assists the completion of a purchase");
        titleList.add("Reassures a user about taking the next action");*/

        adapter = new FaqListAdapter(getActivity(), titleList, dateList, this);
        recyclerViewFaqList.setAdapter(adapter);
        recyclerViewFaqList.setHasFixedSize(true);
        recyclerViewFaqList.setLayoutManager(new LinearLayoutManager(getContext()));
        getAllTitleList();
        return view;
    }

    private void filter(String text) {
        List<FaqTodo> filteredList = new ArrayList<>();
        if (text.toString().isEmpty()) {
            filteredList.addAll(titleList);
        } else {
            for (FaqTodo search : titleList) {
                if (search.getTitle().toLowerCase().contains(text.toString().toLowerCase())) {
                    filteredList.add(search);
                }
            }
        }
        adapter.filterList(filteredList);
    }


    void onClickFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                setCalendar = Calendar.getInstance();
                setCalendar.set(Calendar.YEAR, year);
                setCalendar.set(Calendar.MONTH, month);
                setCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                CharSequence dateString = DateFormat.format("dd/MM/yyyy", setCalendar);
                txtFromDate.setText(dateString);
            }
        }, YEAR, MONTH, DATE);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    void onClickToDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

        try {
            if (setCalendar != null) {
                dialog.getDatePicker().setMinDate(setCalendar.getTimeInMillis());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getActivity(), "Please Select From Date", Toast.LENGTH_SHORT).show();
        }
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    /*@Override
    public void onClickListener(int position,FaqTodo faqTodoFromRv) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.container, new FaqInfo_Fragment(faqTodoFromRv)).addToBackStack(null); //Make suee before you pass this it should be initialized
        transaction.commit();
    }

    @Override
    public void onLongClickListener(int position) {
        titleList.remove(position);
        adapter.notifyDataSetChanged();
    }*/

    public void getAllTitleList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                titleList = DatabaseHelper.getInstance(getActivity()).todoDao().getAllTitleList();
                Log.i(TAG, "run: " + titleList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refresh(titleList);
                    }
                });
            }
        });
        thread.start();
    }

    public void getDateList(String fromDate, String toDate) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dateList = DatabaseHelper.getInstance(getActivity()).todoDao().getDatesList(fromDate, toDate);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refreshDateList(dateList);
                    }
                });
            }
        });
        thread.start();
    }

    /*public void getTodoById() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                faqTodo = DatabaseHelper.getInstance(getActivity()).todoDao().getTodoById(1);
                if (faqTodo != null) {
                    Log.i(TAG, "run: " + faqTodo);
                }
            }
        });
        thread.start();
    }*/

    @Override
    public void faqOnClickListener(int position, FaqTodo faqTodoFromRv) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.container, new FaqInfo_Fragment(faqTodoFromRv)).addToBackStack(null); //Make sure before you pass this it should be initialized
        transaction.commit();
    }
}