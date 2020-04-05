package com.example.shareapy.home.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shareapy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentActivityUpcoming extends Fragment {
    public static AdapterRecyclerviewHistory upcomingAdapter = new AdapterRecyclerviewHistory(false);
    RecyclerView rcvUpcoming;
    public FragmentActivityUpcoming() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_upcoming, null);
        rcvUpcoming = view.findViewById(R.id.rcvUpcoming);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rcvUpcoming.setLayoutManager(llm);
        rcvUpcoming.setAdapter(upcomingAdapter);
        return view;
    }
}
