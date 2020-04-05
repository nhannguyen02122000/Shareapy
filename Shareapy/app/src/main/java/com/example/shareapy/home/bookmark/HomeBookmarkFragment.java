package com.example.shareapy.home.bookmark;

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
import com.example.shareapy.utils.BookmarkActivityRecyclerAdapter;
import com.example.shareapy.utils.CategoryActivity;
import com.example.shareapy.utils.CategoryActivityRecyclerAdapter;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HomeBookmarkFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mFirebaseAuth=  UserSignUp.getInstance().getmFireBaseAuth();
    ArrayList<CategoryActivity> categoryActivities = new ArrayList<>();
    ArrayList<String> bookmark = new ArrayList<>();
    private RecyclerView rvItems;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_bookmark_fragment, container, false);

        db.collection("Users").document(mFirebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        bookmark = (ArrayList<String>)document.getData().get("bookmark");
                        getBookMarkActi(bookmark,view);
                    }
                }
            }
        });

        return view;
    }

    public void getBookMarkActi(ArrayList<String> bookmark, final View view)
    {
        for (final String actiID:bookmark){
            db.collection("ActivityInfos").document(actiID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name = document.getData().get("title").toString().trim();
                            Timestamp time = (Timestamp) document.getData().get("time");
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a dd-MM-yyyy");
                            String date = simpleDateFormat.format(time.toDate());
                            ArrayList<String> registerList = (ArrayList<String>)document.getData().get("registerList");
                            String actiID = document.getId().toString().trim();

                            categoryActivities.add (new CategoryActivity(name,date,registerList,actiID));

                            rvItems = (RecyclerView) view.findViewById(R.id.rv_bookmark);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rvItems.setLayoutManager(layoutManager);
                            rvItems.setHasFixedSize(true);
                            rvItems.setAdapter(new BookmarkActivityRecyclerAdapter(getContext(),categoryActivities,time));
                        }
                    }
                }
            });
        }
    }
}
