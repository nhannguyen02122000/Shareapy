package com.example.shareapy.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shareapy.R;

public class HomeHomeFragment extends Fragment {
    CardView cvHome;
    CalendarView clvHome;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_home_fragment,container,false);
        cvHome = view.findViewById(R.id.cv_home_lifeStyle);
        cvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });
        clvHome = view.findViewById(R.id.clv_home);
        clvHome.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Fragment toEvents = new HomeCalendarEventsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.clv_home,toEvents)
                        .addToBackStack(toEvents.getClass().getSimpleName()).commit();
            }
        });
        return view;
    }
}
