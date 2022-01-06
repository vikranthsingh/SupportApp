package com.example.emrsupportapp.Fragment.TrainingModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emrsupportapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class TrainingList_Fragment extends Fragment {
    EditText etSearchTraining;
    TextView txtFromDateTraining, txtToDateTraining;
    Button btnGoTraining, btnResetTraining;
    RecyclerView recyclerViewTraining;
    FloatingActionButton addFloatingBtnTraining;

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
        return view;
    }
}