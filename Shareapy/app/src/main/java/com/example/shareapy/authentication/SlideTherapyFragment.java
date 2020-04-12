package com.example.shareapy.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shareapy.R;
import com.example.shareapy.models.CurrentUser;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SlideTherapyFragment extends Fragment {
    Button btnPre,btnSignUp;
    UserSignUp userSignUp;
    RadioGroup radioThe;
    RadioButton radioButThe;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;
    //DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = this.getClass().getName();

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
                    userSignUp= UserSignUp.getInstance();
                    int checkedID = radioThe.getCheckedRadioButtonId();
                    if (checkedID == -1 )
                    {
                        Toast.makeText(getContext(),"Please make your choice",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    radioButThe = view.findViewById(checkedID);
                    userSignUp.setTherapy(radioButThe.getText().toString().trim());

                //SignUp
                    progressBar.setVisibility(View.VISIBLE);
                    btnSignUp.setVisibility(View.INVISIBLE);
                    btnPre.setVisibility(View.INVISIBLE);

                    String email = userSignUp.getUserMail();
                    String pass = userSignUp.getPassword();
                    mFirebaseAuth = userSignUp.getmFireBaseAuth();
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful())
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    btnSignUp.setVisibility(View.VISIBLE);
                                    btnPre.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(),"Sign up unsuccessful!",Toast.LENGTH_SHORT).show();
                                    Log.e(TAG,"Error");
                                }
                                else{
                                    Toast.makeText(getContext(),"Sign up successful!",Toast.LENGTH_SHORT).show();
                                    //Add data
                                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                    createDBUser(user);
                                    startActivity(new Intent(getActivity(), LoginFeeling.class));
                                }
                            }
                        });
            }
        });
        return view;
    }

    private void createDBUser(FirebaseUser fbUser) {
        String uid = fbUser.getUid();
        userSignUp.setUserID(uid);
        //DatabaseReference userRef = mRootRef.child("users").child(uid);
        HashMap<String, Object> user = new HashMap<>();
        user.put("name",userSignUp.getUserName());
        user.put("email", userSignUp.getUserMail());
        user.put("gender",userSignUp.getGender());
        user.put("age",userSignUp.getAge());
        user.put("religious",userSignUp.getReligious());
        user.put("spiritual",userSignUp.getSpiritual());
        user.put("therapy",userSignUp.getTherapy());
        user.put("bookmark",userSignUp.getBookmark());
        user.put("feeling","null");
        db.collection("Users").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "Succeeded");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, e.getMessage());
            }
        });

        CurrentUser.userName=userSignUp.getUserName();
        CurrentUser.userID = uid;
        //user.put("name", fbUser.getDisplayName());

//        userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Log.d(TAG, "Succeeded");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, e.getMessage());
//            }
//        });

    }
    private void createView(View view) {
        btnPre = view.findViewById(R.id.btnPre_therapy);
        btnSignUp = view.findViewById(R.id.btnSignUp_therapy);
        radioThe= view.findViewById(R.id.rdgTherapy);
        progressBar = view.findViewById(R.id.pgb_signUp);
    }

}