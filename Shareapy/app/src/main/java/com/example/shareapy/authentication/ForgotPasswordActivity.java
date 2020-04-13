package com.example.shareapy.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.shareapy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextInputLayout tilEmail;
    TextInputEditText tiedtEmail;
    Button btnSend;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        setUpViews();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(v);
            }
        });
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

    private void setUpViews()
    {
        mAuth = FirebaseAuth.getInstance();

        tiedtEmail = findViewById(R.id.tiedtEmail_Forgotpass);
        tilEmail = findViewById(R.id.tilEmail_Forgotpass);
        btnSend = findViewById(R.id.btnSend_ForgotPass);
    }

    private void sendEmail(View v)
    {
        if (!validateInput(tilEmail,tiedtEmail)) return;
        else
        {
            String userEmail = tiedtEmail.getText().toString().trim();
            mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(ForgotPasswordActivity.this,"Please check your Mail to reset password!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ForgotPasswordActivity.this,"Error, please try again",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
