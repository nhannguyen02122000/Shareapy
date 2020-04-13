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
import com.example.shareapy.authentication.ForgotPasswordActivity;
import com.example.shareapy.authentication.LoginActivity;
import com.example.shareapy.models.CurrentUser;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class HomeProfileFragment extends Fragment implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button btn_signOut;
    TextView tv_logOut,tvUserName,tvChangePass,tvUserID;
    FirebaseAuth mFirebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_profile_fragment,container,false);
        setUpViews(view);
        tv_logOut.setOnClickListener(this);
        tvChangePass.setOnClickListener(this);

        return view;
    }

    private void setUpViews(View view)
    {
        mFirebaseAuth = UserSignUp.getInstance().getmFireBaseAuth();
        tv_logOut = view.findViewById(R.id.tv_logOut_home_profile);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvChangePass = view.findViewById(R.id.tv_changePass_Profile);
        tvUserID=view.findViewById(R.id.tvUserID);

        tvUserName.setText(CurrentUser.userName);
        tvUserID.setText(generateText());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_logOut_home_profile:
                FirebaseAuth.getInstance().signOut();
                Intent toLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(toLogin);
                getActivity().finish();
                break;
            case R.id.tv_changePass_Profile:
                startActivity(new Intent(getActivity(), ForgotPasswordActivity.class));
                break;
        }

    }

    private String generateText()
    {
        Random rand = new Random();
        int rand_int = rand.nextInt(1000);
        if (rand_int%3==0)
            return ":) Have a nice day!";
        if (rand_int%3==1)
            return ";) Everything will be OK";
        return "The only impossible thing is impossibility";
    }
}
