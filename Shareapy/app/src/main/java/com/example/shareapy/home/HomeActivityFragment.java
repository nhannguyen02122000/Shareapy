package com.example.shareapy.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shareapy.R;
import com.example.shareapy.homepage.ui.home.FragmentActivityCurrent;
import com.example.shareapy.homepage.ui.home.FragmentActivityHistory;
import com.example.shareapy.homepage.ui.home.FragmentActivityUpcoming;
import com.example.shareapy.homepage.ui.home.AdapterViewpagerActivity;
import com.example.shareapy.models.ActivityInfo;
import com.example.shareapy.utils.SavedInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HomeActivityFragment extends Fragment {
    private TabLayout tlActivity;
    private ViewPager2 vpActivity;
    final String userId = "12356";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<ActivityInfo> activitiesHistory = new ArrayList<>();
    private ArrayList<ActivityInfo> activitiesCurrent = new ArrayList<>();
    private ArrayList<ActivityInfo> activitiesUpcoming = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_activity_fragment,container,false);
        tlActivity = view.findViewById(R.id.tlActivity);
        vpActivity = view.findViewById(R.id.vpActivity);
        SavedInstance.homeActivity = getActivity();
        setupViewPager();
//        fakeData();
        getData();
        return view;
    }

    private void fakeData() {
        CollectionReference activityRef = db.collection("ActivityInfos");
        for (ActivityInfo info: ActivityInfo.makeSample()){
            activityRef.add(info).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.e("HomeAct ", "Success" );
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("HomeAct ", "Failure" );
                }
            });
        }
    }

    private void getData() {
        CollectionReference activityRef = db.collection("ActivityInfos");
        activityRef.whereArrayContains("registerList", userId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Date currentTime = Calendar.getInstance().getTime();
                            for (DocumentSnapshot document : task.getResult()) {
                                ActivityInfo info = document.toObject(ActivityInfo.class);
                                if (info.getTime().getTime() - currentTime.getTime() > 0){
                                    activitiesUpcoming.add(info);
                                } else if (currentTime.getTime() - info.getTime().getTime() > TimeUnit.MINUTES.toMillis(90)){
                                    activitiesHistory.add(info);
                                } else {
                                    activitiesCurrent.add(info);
                                }
                            }
                            FragmentActivityHistory.historyAdapter.setItem(activitiesHistory);
                            FragmentActivityHistory.historyAdapter.notifyDataSetChanged();
                            FragmentActivityUpcoming.upcomingAdapter.setItem(activitiesUpcoming);
                            FragmentActivityUpcoming.upcomingAdapter.notifyDataSetChanged();
                            FragmentActivityCurrent.currentAdapter.setItem(activitiesCurrent);
                            FragmentActivityCurrent.currentAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("hello", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    private void setupViewPager() {
        final AdapterViewpagerActivity adapter = new AdapterViewpagerActivity(getActivity());
        adapter.addFragment(new FragmentActivityHistory());
        adapter.addFragment(new FragmentActivityCurrent());
        adapter.addFragment(new FragmentActivityUpcoming());
        vpActivity.setAdapter(adapter);
        new TabLayoutMediator(tlActivity, vpActivity,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(adapter.getTitle(position));
                    }
                }
        ).attach();
    }
}
