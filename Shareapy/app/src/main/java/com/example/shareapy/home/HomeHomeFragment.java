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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.utils.Category;
import com.example.shareapy.utils.CategoryRecyclerAdapter;
import com.example.shareapy.utils.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeHomeFragment extends Fragment {
    CalendarView clvHome;
    RecyclerView rvCategory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_home_fragment,container,false);

        ArrayList<Category> categories = new ArrayList<Category>() {};
        categories.add(new Category("Lifestyle",R.drawable.home_category_lifestyle));
        categories.add(new Category("Work",R.drawable.home_category_work));
        categories.add(new Category("Relationship",R.drawable.home_category_relationship));
        categories.add(new Category("School",R.drawable.home_category_school));
        categories.add(new Category("Family",R.drawable.home_category_family));
        categories.add(new Category("Other",R.drawable.home_category_other));

        rvCategory = (RecyclerView) view.findViewById(R.id.rv_categories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(new CategoryRecyclerAdapter(getContext(),categories));

        clvHome = view.findViewById(R.id.clv_home);
        clvHome.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Fragment toEvents = new HomeCalendarEventsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.clv_home,toEvents)
                        .addToBackStack(toEvents.getClass().getSimpleName()).commit();
            }
        });
        return view;
    }
}
