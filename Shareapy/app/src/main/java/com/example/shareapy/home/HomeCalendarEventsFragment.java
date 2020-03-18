package com.example.shareapy.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.utils.Events;
import com.example.shareapy.utils.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeCalendarEventsFragment extends Fragment{
    private RecyclerView rvItems;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_calendar_events, container, false);

        List<Events> events = new ArrayList<>();
        events.add(new Events("Header1","Date1","Duration1","Slot1"));
        events.add(new Events("Header2","Date2","Duration2","Slot2"));
        events.add(new Events("Header3","Date3","Duration3","Slot3"));
        events.add(new Events("Header4","Date4","Duration4","Slot4"));

        rvItems = (RecyclerView) view.findViewById(R.id.rv_calendar_event);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setAdapter(new RecyclerAdapter(getContext(),events,this));

        return view;
    }
}
