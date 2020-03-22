package com.example.shareapy.homepage.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.models.ActivityInfo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentActivityHistory extends Fragment {
    public static AdapterRecyclerviewHistory historyAdapter= new AdapterRecyclerviewHistory(true);
    RecyclerView rcvHistory;
    public FragmentActivityHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_history, null);
        rcvHistory = view.findViewById(R.id.rcvHistory);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rcvHistory.setLayoutManager(llm);
        rcvHistory.setAdapter(historyAdapter);
        return view;
    }
}
