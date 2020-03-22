package com.example.shareapy.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.utils.CategoryActivity;
import com.example.shareapy.utils.RecyclerAdapter;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeCalendarEventsFragment extends Fragment{
    private RecyclerView rvItems;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<CategoryActivity> categoryActivities = new ArrayList<>();

    java.sql.Timestamp tsBegin,tsEnd;
    public HomeCalendarEventsFragment(java.sql.Timestamp tsBegin, java.sql.Timestamp tsEnd)
    {
        this.tsBegin = tsBegin;
        this.tsEnd = tsEnd;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_calendar_events, container, false);

        db.collection("ActivityInfos").whereGreaterThanOrEqualTo("time",tsBegin).whereLessThanOrEqualTo("time",tsEnd)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getData().get("title").toString().trim();
                                Timestamp time = (Timestamp) document.getData().get("time");
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a dd-MM-yyyy");
                                String date = simpleDateFormat.format(time.toDate());
                                ArrayList<String> registerList = (ArrayList<String>)document.getData().get("registerList");
                                String actiID = document.getId().toString().trim();
                                categoryActivities.add(new CategoryActivity(name,date,registerList,actiID));

                                rvItems = (RecyclerView) view.findViewById(R.id.rv_calendar_event);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                rvItems.setLayoutManager(layoutManager);
                                rvItems.setHasFixedSize(true);
                                rvItems.setAdapter(new RecyclerAdapter(getContext(),categoryActivities,HomeCalendarEventsFragment.this));
                            }
                        }
                    }
                });
        return view;
    }
}
