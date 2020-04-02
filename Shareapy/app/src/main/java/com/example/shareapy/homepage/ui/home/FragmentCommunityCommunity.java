package com.example.shareapy.homepage.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCommunityCommunity extends Fragment {
//    public static AdapterRecyclerviewCurrent currentAdapter = new AdapterRecyclerviewCurrent();
//    RecyclerView rcvCurrent;
    public FragmentCommunityCommunity() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_community, null);
//        rcvCurrent = view.findViewById(R.id.rcvCurrent);
//        LinearLayoutManager llm = new LinearLayoutManager(getContext());
//        rcvCurrent.setLayoutManager(llm);
//        rcvCurrent.setAdapter(currentAdapter);
        return view;
    }
}
