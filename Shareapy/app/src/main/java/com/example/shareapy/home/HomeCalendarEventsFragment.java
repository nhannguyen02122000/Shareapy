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
import com.example.shareapy.utils.Events;
import com.example.shareapy.utils.RecyclerAdapter;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeCalendarEventsFragment extends Fragment{
    private RecyclerView rvItems;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Events> events = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_calendar_events, container, false);



        db.collection("Events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String duration = document.getData().get("duration").toString().trim();
                                String header = document.getData().get("header").toString().trim();
                                int maxPerson = Integer.parseInt(document.getData().get("maxPerson").toString());
                                ArrayList<String> userIDEnrolled = (ArrayList<String>)document.getData().get("participator");
                                String date = document.getData().get("date").toString();
                                String id = document.getId();
                                events.add(new Events(header,date,duration,maxPerson,id,userIDEnrolled));

                                rvItems = (RecyclerView) view.findViewById(R.id.rv_calendar_event);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                rvItems.setLayoutManager(layoutManager);
                                rvItems.setHasFixedSize(true);
                                rvItems.setAdapter(new RecyclerAdapter(getContext(),events,HomeCalendarEventsFragment.this));
                            }
                        }
                    }
                });
        return view;
    }
}
