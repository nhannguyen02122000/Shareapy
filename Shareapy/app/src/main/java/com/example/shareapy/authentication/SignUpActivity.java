package com.example.shareapy.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shareapy.R;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout tilEmail, tilPassword,tilRePass;
    TextInputEditText tiedtEmail, tiedtPassword,tiedtRePass;
    TextView tvLogin;
    Button btnNext;
    UserSignUp user;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupView();
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

        btnNext.setOnClickListener(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        user = UserSignUp.getInstance();
        mFirebaseAuth = user.getmFireBaseAuth();
    }

    private void setupView()
    {
        tilEmail = findViewById(R.id.tilEmail_signUp);
        tilPassword = findViewById(R.id.tilPassword_signUp);
        tilRePass = findViewById(R.id.tilRePassword_signUp);
        tiedtEmail=findViewById(R.id.tiedtEmail_signUp);
        tiedtPassword = findViewById(R.id.tiedtPassword_signUp);
        tiedtRePass = findViewById(R.id.tiedtRePassword_signUp);
        tvLogin = findViewById(R.id.tv_logIn_signUp);
        btnNext = findViewById(R.id.btnNext_signUp);
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

    public void signUp(View v)
    {
        String email = tiedtEmail.getText().toString().trim();
        String pass = tiedtPassword.getText().toString().trim();
        String rePass = tiedtRePass.getText().toString().trim();
        boolean equalStr = tiedtPassword.getText().toString().trim().equals(tiedtRePass.getText().toString().trim());
        if (!validateInput(tilEmail,tiedtEmail)) return;
        else if (!validateInput(tilPassword,tiedtPassword)) return;
        else if (!validateInput(tilRePass,tiedtRePass)) return;
        else if (pass.length()<6)
        {
            tilPassword.setError(getString(R.string.min_pass_requirement));
            requestFocus(tiedtPassword);
            return;
        }
        else if (!equalStr)
        {
            tilRePass.setError(getString(R.string.error_differentString));
            requestFocus(tiedtRePass);
            return;
        }
        else if (equalStr)
        {
            user.setUserMail(email);
            user.setPassword(pass);
            startActivity(new Intent(SignUpActivity.this,SignUpSlides.class));
        }
        else {
            Toast.makeText(SignUpActivity.this,"Error occured!",Toast.LENGTH_SHORT).show();}
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnNext_signUp:
                signUp(v);
                break;
        }
    }
}
