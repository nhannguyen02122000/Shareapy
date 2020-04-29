package com.example.shareapy.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shareapy.R;
import com.example.shareapy.home.Home;
import com.example.shareapy.models.CurrentUser;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LoginFeeling extends AppCompatActivity {
    Button btnNext;
    TextView tvHello,tvASK;
    SmileRating smileRating;
    UserSignUp userSignUp;
    String rate ;
    ProgressBar progressBar;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CurrentUser.openFeeling=false) finish();
        setContentView(R.layout.activity_login_feeling);
        setupView();


        FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
        String uid = fbUser.getUid();
//        db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
//                        String name = document.getData().get("name").toString().trim();
//                        tvHello.setText("Hello, "+name);
//                    }
//                }
//            }
//        });

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                        rate = "BAD";
                        break;
                    case SmileRating.GOOD:
                        rate = "GOOD";
                        break;
                    case SmileRating.GREAT:
                        rate="GREAT";
                        break;
                    case SmileRating.OKAY:
                        rate = "OKAY";
                        break;
                    case SmileRating.TERRIBLE:
                        rate = "TERRIBLE";
                        break;
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rate.isEmpty())
                    Toast.makeText(LoginFeeling.this,"Please make your choice",Toast.LENGTH_SHORT).show();
                else
                {
                    showProgressbar();
                    SimpleDateFormat sdf = new SimpleDateFormat("h:mm a dd-MM-yyyy");
                    String currentDateandTime = sdf.format(new Date());

                    FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
                    String uid = fbUser.getUid();

                    HashMap<String, Object> userFeeling = new HashMap<>();
                    userFeeling.put(currentDateandTime,rate);

                    db.collection("Users").document(uid).collection("Feelings").document("DateFeeling").set(userFeeling, SetOptions.merge());
                    CurrentUser.openFeeling=true;
                    startActivity(new Intent(LoginFeeling.this, Home.class));
                    finish();
                }
            }
        });
    }

    protected void setupView()
    {
        rate="";
        mFirebaseAuth = UserSignUp.getInstance().getmFireBaseAuth();
        btnNext = findViewById(R.id.btnNext_logIn_ask);
        smileRating = findViewById(R.id.smile_rating);
        tvHello = findViewById(R.id.tv_login_hello);
        tvASK = findViewById(R.id.tv_logIn_ask);
        progressBar = findViewById(R.id.pgb_Feeling);

        tvHello.setText("Hello, "+ CurrentUser.userName);
    }
    private void hideProgressBar()
    {
        progressBar.setVisibility(View.INVISIBLE);
        tvASK.setVisibility(View.VISIBLE);
        smileRating.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
    }
    private void showProgressbar()
    {
        progressBar.setVisibility(View.VISIBLE);
        tvASK.setVisibility(View.INVISIBLE);
        smileRating.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
    }
}
