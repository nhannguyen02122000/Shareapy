package com.example.shareapy.home.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shareapy.R;
import com.example.shareapy.authentication.LoginActivity;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeProfileFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button btn_signOut;
    TextView tv_logOut,tvUserName;
    FirebaseAuth mFirebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_profile_fragment,container,false);
        mFirebaseAuth = UserSignUp.getInstance().getmFireBaseAuth();
        tv_logOut = view.findViewById(R.id.tv_logOut_home_profile);
        tv_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent toLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(toLogin);
                getActivity().finish();
            }
        });
        tvUserName = view.findViewById(R.id.tvUserName);
        FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
        String uid = fbUser.getUid();
        db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        String name = document.getData().get("name").toString().trim();
                        tvUserName.setText(name);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
        return view;
    }

}
