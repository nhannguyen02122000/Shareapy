package com.example.shareapy.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shareapy.R;
import com.example.shareapy.home.Home;
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
    TextView tvHello;
    SmileRating smileRating;
    UserSignUp userSignUp;
    String rate ;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_feeling);
        setupView();


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
                        tvHello.setText("Hello, "+name);
                    }
                }
            }
        });

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
                    SimpleDateFormat sdf = new SimpleDateFormat("h:mm a dd-MM-yyyy");
                    String currentDateandTime = sdf.format(new Date());

                    FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
                    String uid = fbUser.getUid();

                    HashMap<String, Object> userFeeling = new HashMap<>();
                    userFeeling.put(currentDateandTime,rate);

                    db.collection("Users").document(uid).collection("Feelings").document("DateFeeling").set(userFeeling, SetOptions.merge());
                    startActivity(new Intent(LoginFeeling.this, Home.class));
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
    }
}
