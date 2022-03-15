package com.example.emrsupportapp.Fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.emrsupportapp.Adapter.QueryListAdapter;
import com.example.emrsupportapp.Fragment.FaqModule.FaqList_Fragment;
import com.example.emrsupportapp.Fragment.RaisedTickets.RaisedTicket_Fragment;
import com.example.emrsupportapp.Fragment.TrainingModule.TrainingList_Fragment;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.interfaces.RecyclerviewOnClickListener;


public class QueryListFragment extends Fragment implements RecyclerviewOnClickListener {

    private static final String TAG = "TAG";
    RecyclerView recyclerviewQueryFragment;
    String[] queryList = {"Frequent asked Questions", "Training Module", "Raised Tickets List"};
    int[] queryImages = {R.drawable.questions, R.drawable.question, R.drawable.tickets};
    FaqList_Fragment faqListFragment;
    TrainingList_Fragment trainingFragment;
    FragmentManager manager;
    RaisedTicket_Fragment raisedTicketFragment;
    String vgModule, vtModule, cspModule, moduleType;
    Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query_list, container, false);
        recyclerviewQueryFragment = view.findViewById(R.id.recyclerviewQueryFragment);
        QueryListAdapter adapter = new QueryListAdapter(getContext(), queryList, queryImages, this);
        recyclerviewQueryFragment.setAdapter(adapter);
        recyclerviewQueryFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerviewQueryFragment.setHasFixedSize(true);
        bundle = this.getArguments();
        if (bundle != null) {
            moduleType = bundle.getString("moduleType");  //Like this you have to get module type. compare with "moduleType" field. It may contain VG,VT or SCP based on Click.
           /* String vgModule = bundle.getString("VG");
            String vtModule = bundle.getString("VT");
            String cspModule = bundle.getString("CSP");
            Log.d(TAG, "onCreateView: " + vgModule + " " + vtModule + " " + cspModule);*/
        }

        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return view;
    }

    @Override
    public void onClickListener(int position) {
        switch (position) {
            case 0:
                Toast.makeText(getActivity(), "Frequent asked Questions", Toast.LENGTH_SHORT).show();
                faqListFragment = new FaqList_Fragment();
                bundle = new Bundle();
                bundle.putString(moduleType, moduleType);
                Log.i(TAG, "onClickListener: " + moduleType);
                faqListFragment.setArguments(bundle);
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, faqListFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                Toast.makeText(getActivity(), "Training Module", Toast.LENGTH_SHORT).show();
                trainingFragment = new TrainingList_Fragment();
                bundle = new Bundle();
                bundle.putString(moduleType, moduleType);
                Log.i(TAG, "onClickListener: " + moduleType);
                trainingFragment.setArguments(bundle);
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, trainingFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                Toast.makeText(getActivity(), "Raised Tickets List", Toast.LENGTH_SHORT).show();
                raisedTicketFragment = new RaisedTicket_Fragment();
                bundle = new Bundle();
                bundle.putString(moduleType, moduleType);  // Its common for all modules.
                Log.i(TAG, "onClickListener: " + moduleType);
                raisedTicketFragment.setArguments(bundle); //get module Type from bundle in respective fragment.
                manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, raisedTicketFragment)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}