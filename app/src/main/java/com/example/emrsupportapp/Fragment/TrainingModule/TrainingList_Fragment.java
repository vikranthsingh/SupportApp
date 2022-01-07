package com.example.emrsupportapp.Fragment.TrainingModule;

import android.app.DatePickerDialog;
import android.os.Bundle;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emrsupportapp.Adapter.TrainingListAdapter;
import com.example.emrsupportapp.Fragment.FaqModule.FaqInfo_Fragment;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.activities.DatabaseHelper;
import com.example.emrsupportapp.activities.TrainingTodo;
import com.example.emrsupportapp.interfaces.TrainingOnClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TrainingList_Fragment extends Fragment implements TrainingOnClickListener {
    EditText etSearchTraining;
    TextView txtFromDateTraining, txtToDateTraining;
    Button btnGoTraining, btnResetTraining;
    RecyclerView recyclerViewTraining;
    FloatingActionButton addFloatingBtnTraining;
    TrainingListAdapter adapter;
    List<TrainingTodo> titleList = new ArrayList<>();
    List<TrainingTodo> dateList = new ArrayList<>();
    private static final String TAG = "TAG";
    Calendar setCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training_list, container, false);
        etSearchTraining = view.findViewById(R.id.etSearchTraining);
        txtFromDateTraining = view.findViewById(R.id.txtFromDateTraining);
        txtToDateTraining = view.findViewById(R.id.txtToDateTraining);
        btnGoTraining = view.findViewById(R.id.btnGoTraining);
        btnResetTraining = view.findViewById(R.id.btnResetTraining);
        recyclerViewTraining = view.findViewById(R.id.recyclerViewTraining);
        addFloatingBtnTraining = view.findViewById(R.id.addFloatingBtnTraining);
        addFloatingBtnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null).replace(R.id.container, new AddTraining_Fragment());
                transaction.commit();
            }
        });

        txtFromDateTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFromDatePicker();
            }
        });
        txtToDateTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToDatePicker();
            }
        });
        etSearchTraining.addTextChangedListener(new TextWatcher() {
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
        btnGoTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDateTraining = txtFromDateTraining.getText().toString();
                String toDateTraining = txtToDateTraining.getText().toString();
                Toast.makeText(getActivity(), "From Date : "+fromDateTraining + "\nTo Date : " + toDateTraining, Toast.LENGTH_SHORT).show();
                getDateListTraining(fromDateTraining, toDateTraining);

                if (txtFromDateTraining.getText().toString().isEmpty()) {
                    txtFromDateTraining.setBackgroundResource(R.drawable.custom_error_background);
                    txtFromDateTraining.setError("Please choose From date");
                } else {
                    txtFromDateTraining.setBackgroundResource(R.drawable.edittext_background);
                    txtFromDateTraining.setError(null);
                }
                if (txtToDateTraining.getText().toString().isEmpty()) {
                    txtToDateTraining.setBackgroundResource(R.drawable.custom_error_background);
                    txtToDateTraining.setError("Please choose To date");
                } else {
                    txtToDateTraining.setBackgroundResource(R.drawable.edittext_background);
                    txtToDateTraining.setError(null);
                }
            }
        });
        btnResetTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFromDateTraining.setText("");
                txtToDateTraining.setText("");
                etSearchTraining.setText("");
            }
        });
        adapter = new TrainingListAdapter(getActivity(), titleList);
        recyclerViewTraining.setAdapter(adapter);
        recyclerViewTraining.setHasFixedSize(true);
        recyclerViewTraining.setLayoutManager(new LinearLayoutManager(getContext()));
        getTrainingTitleList();
        return view;
    }

    public void getTrainingTitleList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                titleList = DatabaseHelper.getInstance(getActivity()).todoDao().getTrainingTitleList();
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
        List<TrainingTodo> filteredList = new ArrayList<>();
        if (text.toString().isEmpty()) {
            filteredList.addAll(titleList);
        } else {
            for (TrainingTodo search : titleList) {
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
                txtFromDateTraining.setText(dateString);
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
                txtToDateTraining.setText(dateString);
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

    public void getDateListTraining(String fromDate, String toDate) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dateList = DatabaseHelper.getInstance(getActivity()).todoDao().getTrainingDatesList(fromDate, toDate);
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

    @Override
    public void trainingOnClickListener(int position, TrainingTodo trainingTodo) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.container, new TrainingInfo_Fragment(trainingTodo)).addToBackStack(null); //Make sure before you pass this it should be initialized
        transaction.commit();
    }
}