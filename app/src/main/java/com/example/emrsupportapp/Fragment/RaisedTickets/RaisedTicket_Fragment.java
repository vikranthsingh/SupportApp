package com.example.emrsupportapp.Fragment.RaisedTickets;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emrsupportapp.Adapter.TicketListAdapter;
import com.example.emrsupportapp.Fragment.FaqModule.FaqInfo_Fragment;
import com.example.emrsupportapp.Fragment.FaqModule.FaqList_Fragment;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.activities.DatabaseHelper;
import com.example.emrsupportapp.activities.FaqTodo;
import com.example.emrsupportapp.activities.TicketTodo;
import com.example.emrsupportapp.interfaces.TicketOnClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RaisedTicket_Fragment extends Fragment implements View.OnClickListener, TicketOnClickListener, TicketListAdapter.RecyclerOnClickListener {
    FloatingActionButton addFloatingBtn;
    TextView txtAll, txtNew, txtInProgress, txtResolved, txtRejected;
    TextView txtFromDate, txtToDate;
    TicketListAdapter adapter;
    List<TicketTodo> titleList = new ArrayList<>();
    List<TicketTodo> dateList = new ArrayList<>();
    List<TicketTodo> statusList = new ArrayList<>();
    RecyclerView recyclerViewTicketList;
    private static final String TAG = "TAG";
    Calendar setCalendar;
    Button btnGo, btnReset;
    EditText etSearch;
    String moduleType;
    Bundle bundle;
    RaisedTicket_Fragment raisedTicketFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_raised_ticket, container, false);
        addFloatingBtn = view.findViewById(R.id.addFloatingBtn);
        txtAll = view.findViewById(R.id.txtAll);
        txtNew = view.findViewById(R.id.txtNew);
        txtInProgress = view.findViewById(R.id.txtInProgress);
        txtResolved = view.findViewById(R.id.txtResolved);
        txtRejected = view.findViewById(R.id.txtRejected);
        txtFromDate = view.findViewById(R.id.txtFromDate);
        txtToDate = view.findViewById(R.id.txtToDate);
        btnGo = view.findViewById(R.id.btnGo);
        btnReset = view.findViewById(R.id.btnReset);
        etSearch = view.findViewById(R.id.etSearch);
        recyclerViewTicketList = view.findViewById(R.id.recyclerViewTicketList);
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
        txtAll.setOnClickListener(this);
        txtNew.setOnClickListener(this);
        txtInProgress.setOnClickListener(this);
        txtResolved.setOnClickListener(this);
        txtRejected.setOnClickListener(this);
        addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null).replace(R.id.container, new AddTicket_Fragment(null));
                transaction.commit();
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDate = txtFromDate.getText().toString();
                String toDate = txtToDate.getText().toString();
                getDateList(fromDate, toDate);

                if (txtFromDate.getText().toString().isEmpty()) {
                    txtFromDate.setBackgroundResource(R.drawable.custom_error_background);
                    txtFromDate.setError("Please choose From date");
                } else {
                    txtFromDate.setBackgroundResource(R.drawable.edittext_background);
                    txtFromDate.setError(null);
                }
                if (txtToDate.getText().toString().isEmpty()) {
                    txtToDate.setBackgroundResource(R.drawable.custom_error_background);
                    txtToDate.setError("Please choose To date");
                } else {
                    txtToDate.setBackgroundResource(R.drawable.edittext_background);
                    txtToDate.setError(null);
                }
            }
        });
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
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFromDate.setText("");
                txtToDate.setText("");
                etSearch.setText("");
            }
        });
        bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle != null) {
            moduleType = bundle.getString("moduleType");
        }
        raisedTicketFragment = new RaisedTicket_Fragment();
        bundle.putString("moduleType", moduleType);
        raisedTicketFragment.setArguments(bundle);

        adapter = new TicketListAdapter(getActivity(), titleList, this, this);
        recyclerViewTicketList.setAdapter(adapter);
        recyclerViewTicketList.setHasFixedSize(true);
        recyclerViewTicketList.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAllTitleList();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtAll:
                getAllTitleList();
                Toast.makeText(getActivity(), "All", Toast.LENGTH_SHORT).show();
                txtAll.setBackgroundResource(R.drawable.custom_textview_bg);
                txtNew.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtInProgress.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtResolved.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtRejected.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                break;
            case R.id.txtNew:
                getStatusList("New");
                Toast.makeText(getActivity(), "New", Toast.LENGTH_SHORT).show();
                txtAll.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtNew.setBackgroundResource(R.drawable.custom_textview_bg);
                txtInProgress.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtResolved.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtRejected.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                break;
            case R.id.txtInProgress:
                getStatusList("In-Progress");
                Toast.makeText(getActivity(), "InProgress", Toast.LENGTH_SHORT).show();
                txtAll.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtNew.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtInProgress.setBackgroundResource(R.drawable.custom_textview_bg);
                txtResolved.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtRejected.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                break;
            case R.id.txtResolved:
                getStatusList("Resolved");
                Toast.makeText(getActivity(), "Resolved", Toast.LENGTH_SHORT).show();
                txtAll.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtNew.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtInProgress.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtResolved.setBackgroundResource(R.drawable.custom_textview_bg);
                txtRejected.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                break;
            case R.id.txtRejected:
                getStatusList("Rejected");
                Toast.makeText(getActivity(), "Rejected", Toast.LENGTH_SHORT).show();
                txtAll.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtNew.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtInProgress.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtResolved.setBackgroundResource(R.drawable.custom_textview_bg_unselected);
                txtRejected.setBackgroundResource(R.drawable.custom_textview_bg);
                break;
        }
    }

    public void getAllTitleList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                titleList = DatabaseHelper.getInstance(getActivity()).todoDao().getAllTitleListTicket();
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

    private void filter(String text) {
        List<TicketTodo> filteredList = new ArrayList<>();
        if (text.toString().isEmpty()) {
            filteredList.addAll(statusList);
        } else {
            for (TicketTodo search : statusList) {
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
                CharSequence dateString = DateFormat.format("yyyy-MM-dd", setCalendar);
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
                CharSequence dateString = DateFormat.format("yyyy-MM-dd", calendar1);
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

    public void getDateList(String fromDate, String toDate) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dateList = DatabaseHelper.getInstance(getActivity()).todoDao().getDatesListTicket(fromDate, toDate);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refresh(dateList);
                    }
                });
            }
        });
        thread.start();
    }

    public void getStatusList(String status) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                statusList = DatabaseHelper.getInstance(getActivity()).todoDao().getStatusList(status);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refresh(statusList);
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public void onClickListenerTicket(int position, TicketTodo ticketTodo) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.container, new TicketInfo_Fragment(ticketTodo)).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClickItem(int position, TicketTodo ticketTodo) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.container, new AddTicket_Fragment(ticketTodo)).addToBackStack(null);
        transaction.commit();
    }

    /*@Override
    public void onClickAddToFaq(int position) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.container, new FaqList_Fragment()).addToBackStack(null);
        transaction.commit();
    }*/
    /*@Override
    public void onLongClickListenerTicket(int position, TicketTodo ticketTodo) {
        Toast.makeText(getActivity(), "OnLongClick", Toast.LENGTH_SHORT).show();
        *//*FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.container, new AddTicket_Fragment()).addToBackStack(null);
        transaction.commit();*//*
    }*/
}