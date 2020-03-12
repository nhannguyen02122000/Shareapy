package com.example.shareapy.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shareapy.MainActivity;
import com.example.shareapy.R;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SlideTherapyFragment extends Fragment {
    Button btnPre,btnSignUp;
    UserSignUp user;
    RadioGroup radioThe;
    RadioButton radioButThe;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.signup_slide_therapy,container,false);
        createView(view);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment toSpi = new SlideSpiritualFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,toSpi).commit();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for validation
                    user = UserSignUp.getInstance();
                    int checkedID = radioThe.getCheckedRadioButtonId();
                    if (checkedID == -1 )
                    {
                        Toast.makeText(getContext(),"Please make your choice",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    radioButThe = view.findViewById(checkedID);
                    user.setTherapy(radioButThe.getText().toString().trim());
                //Add Data
                    DatabaseReference mUser = mRootRef.child(user.getUserID());
                    mUser.child("Gender").setValue(user.getGender());
                    mUser.child("Therapy").setValue(user.getTherapy());
                //SignUp
                    String email = user.getUserID();
                    String pass = user.getPassword();
//                    mFirebaseAuth = user.getmFireBaseAuth();
//                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (!task.isSuccessful())
//                                {
//                                    Toast.makeText(getContext(),"Sign up unsuccessful!",Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(getContext(),"Sign up successful!",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                //Change to main activity
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
            }
        });
        return view;
    }
    private void createView(View view) {
        btnPre = view.findViewById(R.id.btnPre_therapy);
        btnSignUp = view.findViewById(R.id.btnSignUp_therapy);
        radioThe= view.findViewById(R.id.rdgTherapy);
    }


//    private void addUserData()
//    {
//
//    }
}