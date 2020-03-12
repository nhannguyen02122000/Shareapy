package com.example.shareapy.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shareapy.MainActivity;
import com.example.shareapy.R;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout tilEmail, tilPassword;
    TextInputEditText tiedtEmail, tiedtPassword; //IMPORTANT: PASSWORD must have at least 6 chars
    Button btnSignUp,btnLogIn;
    FirebaseAuth mFireBaseAuth;
    static boolean hasLogIn = false;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupView();

        mFireBaseAuth = UserSignUp.getInstance().getmFireBaseAuth();

        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        //Check whether there exist user logged in
        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser mFirebaseUser = mFireBaseAuth.getCurrentUser();
            if (mFirebaseUser != null && !hasLogIn)
            {
                Toast.makeText(LoginActivity.this,"You have already logged in!",Toast.LENGTH_SHORT).show();
                hasLogIn=true;
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
            else
            {
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
        if (!validateInput(tilEmail,tiedtEmail)) return;
        else if (!validateInput(tilPassword,tiedtPassword)) return;
        else if (validateInput(tilEmail,tiedtEmail) && validateInput(tilPassword,tiedtPassword))
        {
            mFireBaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this,"Login failed!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Login successful!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        else  {Toast.makeText(LoginActivity.this,"Error occured!",Toast.LENGTH_SHORT).show();}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp_logIn:
                Intent toSignUp = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(toSignUp);
                break;
            case R.id.btnLogIn_logIn:
                logIn(v);
                break;
        }
    }


}
