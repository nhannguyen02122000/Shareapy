package com.example.shareapy.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shareapy.R;
import com.example.shareapy.models.CurrentUser;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity{
    TextInputLayout tilEmail, tilPassword;
    LinearLayout llLogIn;
    TextInputEditText tiedtEmail, tiedtPassword; //IMPORTANT: PASSWORD must have at least 6 chars
    Button btnSignUp,btnLogIn;
    ProgressBar progressBar;
    TextView tvForgotPass;

    FirebaseAuth mFireBaseAuth = UserSignUp.getInstance().getmFireBaseAuth();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupView();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignUp = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(toSignUp);
                finish();
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgessbar();
                logIn(v);
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        //Check whether there exist user logged in
        checkUserExistence();

    }
    private void checkUserExistence()
    {
        showProgessbar();
        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFireBaseAuth.getCurrentUser();
                if (mFirebaseUser != null )
                {
                    CurrentUser.userID=mFirebaseUser.getUid();
                    db.collection("Users").document(CurrentUser.userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String name = document.getData().get("name").toString().trim();
                                    CurrentUser.userName=name;
//                                Toast.makeText(LoginActivity.this,"You have already logged in!",Toast.LENGTH_SHORT).show();
                                    if (CurrentUser.openFeeling)
                                    {
                                        startActivity(new Intent(LoginActivity.this, LoginFeeling.class));
                                        CurrentUser.openFeeling=false;
                                    }
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                                    hideProgressbar();
                                }
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                                hideProgressbar();
                            }
                        }
                    });

                }
                else
                {
                    hideProgressbar();
                    //Toast.makeText(LogInScreen.this,"Please login",Toast.LENGTH_SHORT).show();
                }
            }};
        mFireBaseAuth.addAuthStateListener(mAuthStateListener);
    }
    private void setupView() {
        tilEmail = findViewById(R.id.tilEmail_logIn);
        tilPassword = findViewById(R.id.tilPassword_logIn);
        tiedtEmail = findViewById(R.id.tiedtEmail_logIn);
        tiedtPassword = findViewById(R.id.tiedtPassword_logIn);
        btnSignUp = findViewById(R.id.btnSignUp_logIn);
        btnLogIn=findViewById(R.id.btnLogIn_logIn);
        progressBar = findViewById(R.id.prg_login);
        llLogIn = findViewById(R.id.ly_logIn);
        tvForgotPass = findViewById(R.id.tv_forgotPassword);
    }
    private void showProgessbar()
    {
        progressBar.setVisibility(View.VISIBLE);
        llLogIn.setVisibility(View.INVISIBLE);
    }
    private void hideProgressbar()
    {
        progressBar.setVisibility(View.INVISIBLE);
        llLogIn.setVisibility(View.VISIBLE);
    }
    private void requestFocus(View view){
        if (view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private boolean validateInput(TextInputLayout til , TextInputEditText tiedt){
        if (tiedt.getText().toString().trim().isEmpty()){
            til.setError(getString(R.string.error_emptyFiled));
            requestFocus(tiedt);
            return false;
        }
        return true;
    }

    public void logIn(View view) {
//        if (!validateEmail()) return;
//        Toast.makeText(LoginActivity.this, "Login success",Toast.LENGTH_SHORT).show();

        String email = tiedtEmail.getText().toString().trim();
        String pass = tiedtPassword.getText().toString().trim();
//                Log.e("email",email);
//                Log.e("pass",pass);
        if (!validateInput(tilEmail,tiedtEmail))
        {
            hideProgressbar();
            return;
        }
        else if (!validateInput(tilPassword,tiedtPassword)) {
            hideProgressbar();
            return;
        }
        else if (validateInput(tilEmail,tiedtEmail) && validateInput(tilPassword,tiedtPassword))
        {
            mFireBaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        hideProgressbar();
                        Toast.makeText(LoginActivity.this,"Login failed!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        CurrentUser.userID=mFireBaseAuth.getCurrentUser().getUid();
                        db.collection("Users").document(CurrentUser.userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String name = document.getData().get("name").toString().trim();
                                        CurrentUser.userName=name;
                                        Toast.makeText(LoginActivity.this,"Login successful!",Toast.LENGTH_SHORT).show();
                                        if (CurrentUser.openFeeling)
                                        {
                                            Intent intent = new Intent(LoginActivity.this,LoginFeeling.class);
                                            CurrentUser.openFeeling=false;
                                            startActivity(intent);
                                        }
                                        finish();
                                    }
                                    else
                                    {
                                        hideProgressbar();
                                        Toast.makeText(LoginActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    hideProgressbar();
                                    Toast.makeText(LoginActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            });
        }
        else  {
            hideProgressbar();
            Toast.makeText(LoginActivity.this,"Error occured!",Toast.LENGTH_SHORT).show();
        }
    }




}
