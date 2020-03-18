package com.example.shareapy.homepage.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shareapy.R;
import com.example.shareapy.utils.SavedInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentActivityCurrent extends Fragment {
    public static AdapterRecyclerviewCurrent currentAdapter = new AdapterRecyclerviewCurrent();
    RecyclerView rcvCurrent;
    public FragmentActivityCurrent() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_current, null);
        rcvCurrent = view.findViewById(R.id.rcvCurrent);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rcvCurrent.setLayoutManager(llm);
        rcvCurrent.setAdapter(currentAdapter);
        return view;
    }
}
