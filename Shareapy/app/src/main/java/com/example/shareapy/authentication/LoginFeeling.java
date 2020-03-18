package com.example.shareapy.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shareapy.R;
import com.example.shareapy.home.Home;
import com.example.shareapy.utils.UserSignUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LoginFeeling extends AppCompatActivity {
    Button btnNext;
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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                    String currentDateandTime = sdf.format(new Date());

                    FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
                    String uid = fbUser.getUid();

                    HashMap<String, Object> userFeeling = new HashMap<>();
                    userFeeling.put(currentDateandTime,rate);

                    db.collection("Users").document(uid).collection("Feelings").document().set(userFeeling, SetOptions.merge());
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
    }
}
