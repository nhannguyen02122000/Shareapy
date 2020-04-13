package com.example.shareapy.home.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.shareapy.R;
import com.example.shareapy.models.CurrentUser;
import com.example.shareapy.utils.Category;
import com.example.shareapy.utils.CategoryRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeHomeFragment extends Fragment {
    SwipeRefreshLayout swpLayout;
    TextView tvWelcome;
    RelativeLayout rlCalendar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Category> categories = new ArrayList<Category>() {};
    ArrayList<EventDay> events = new ArrayList<>();
    RecyclerView rvCategory;
    ProgressBar progressBar_cld,progressBar_category;
    com.applandeo.materialcalendarview.CalendarView clvHomeMaterial;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_home_fragment,container,false);

        setUpViews(view);
        setUpCategoryList();
        getCalendarEventsData();

        swpLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                events.clear();
                getCalendarEventsData();
                swpLayout.setRefreshing(false);
            }
        });

        clvHomeMaterial.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                Calendar calendar = eventDay.getCalendar();
                long timeBegin = calendar.getTimeInMillis();
                long timeEnd = calendar.getTimeInMillis()+24*60*60*1000-1000;
                Timestamp tsBegin = new Timestamp(timeBegin);
                Timestamp tsEnd = new Timestamp(timeEnd);


                Fragment toEvents = new HomeCalendarEventsFragment(tsBegin,tsEnd);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rl_put_events,toEvents)
                        .addToBackStack(toEvents.getClass().getSimpleName()).commit();
            }
        });

        return view;
    }
    private void getCalendarEventsData()
    {
        showProgressBarCalendar();
        db.collection("ActivityInfos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        com.google.firebase.Timestamp time = (com.google.firebase.Timestamp) document.getData().get("time");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(time.toDate());

                        events.add(new EventDay(calendar,R.drawable.ic_circle_green_24dp));

                    }
                }
                hideProgressBarCalendar();
                clvHomeMaterial.setEvents(events);
            }
        });
    }
    private void setUpCategoryList()
    {
        categories.add(new Category("Lifestyle",R.drawable.home_category_lifestyle));
        categories.add(new Category("Work",R.drawable.home_category_work));
        categories.add(new Category("Relationship",R.drawable.home_category_relationship));
        categories.add(new Category("School",R.drawable.home_category_school));
        categories.add(new Category("Family",R.drawable.home_category_family));
        categories.add(new Category("Other",R.drawable.home_category_other));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(new CategoryRecyclerAdapter(getContext(),categories));
    }
    private void setUpViews(View view)
    {
        swpLayout = view.findViewById(R.id.SwipeRefreshHome);
        rlCalendar = view.findViewById(R.id.rl_Calendar);
        progressBar_cld = view.findViewById(R.id.pgb_Calendar);
        progressBar_category = view.findViewById(R.id.pgb_category);
        tvWelcome = view.findViewById(R.id.tv_home_home_welcome);
        tvWelcome.setText("Welcome,\n"+ CurrentUser.userName );
        clvHomeMaterial = view.findViewById(R.id.clv_home_material);
        rvCategory = (RecyclerView) view.findViewById(R.id.rv_categories);
    }
    private void hideProgressBarCalendar()
    {
        progressBar_cld.setVisibility(View.INVISIBLE);
        rlCalendar.setVisibility(View.VISIBLE);

    }
    private void showProgressBarCalendar()
    {
        progressBar_cld.setVisibility(View.VISIBLE);
        rlCalendar.setVisibility(View.INVISIBLE);
    }

}
