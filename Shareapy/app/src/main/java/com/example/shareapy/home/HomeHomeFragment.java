package com.example.shareapy.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

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
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeHomeFragment extends Fragment {
    TextView tvWelcome;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mFirebaseAuth=  UserSignUp.getInstance().getmFireBaseAuth();
    CalendarView clvHome;
    RecyclerView rvCategory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_home_fragment,container,false);

        tvWelcome = view.findViewById(R.id.tv_home_home_welcome);
        FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
        String uid = fbUser.getUid();
        db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = document.getData().get("name").toString().trim();
                        tvWelcome.setText("Welcome,\n"+name+"!");
                    }
                }
            }
        });

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
