package com.example.shareapy.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.utils.CategoryActivity;
import com.example.shareapy.utils.CategoryActivityRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HomeCategoryActivityFragment extends Fragment {
    String activityType;
    private RecyclerView rvItems;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<CategoryActivity> categoryActivities = new ArrayList<>();

    public HomeCategoryActivityFragment(String activityType)
    {
        this.activityType=activityType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_category_activity_fragment, container, false);

        db.collection("ActivityInfos").whereEqualTo("type",activityType)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int size = task.getResult().size();
                            if (size == 0)
                            {
//                                Fragment noData = new NoDataFragment();
//                                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_category_container,noData)
//                                        .addToBackStack(noData.getClass().getSimpleName()).commit();
                                Toast.makeText(getContext(),"Sorry, no data was found",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getData().get("title").toString().trim();
                                    Timestamp time = (Timestamp) document.getData().get("time");
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a dd-MM-yyyy");
                                    String date = simpleDateFormat.format(time.toDate());
                                    ArrayList<String> registerList = (ArrayList<String>)document.getData().get("registerList");
                                    String actiID = document.getId().toString().trim();

                                    categoryActivities.add (new CategoryActivity(name,date,registerList,actiID));

                                    rvItems = (RecyclerView) view.findViewById(R.id.rv_category_activity);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                    rvItems.setLayoutManager(layoutManager);
                                    rvItems.setHasFixedSize(true);
                                    rvItems.setAdapter(new CategoryActivityRecyclerAdapter(getContext(),categoryActivities,time));
                                }
                            }
                        }
                    }
                });

        return view;
    }
}
