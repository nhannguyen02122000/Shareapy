package com.example.shareapy.authentication;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shareapy.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout tilEmail, tilPassword;
    TextInputEditText tiedtEmail, tiedtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupView();
    }

    private void setupView() {
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tiedtEmail = findViewById(R.id.tiedtEmail);
        tiedtPassword = findViewById(R.id.tiedtPassword);
    }

    private void requestFocus(View view){
        if (view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateEmail(){
        if (tiedtEmail.getText().toString().trim().isEmpty()){
            tilEmail.setError(getString(R.string.login_errorEmail));
            requestFocus(tiedtEmail);
            return false;
        }
        return true;
    }


    public void logIn(View view) {
        if (!validateEmail()) return;
        Toast.makeText(LoginActivity.this, "Login success",Toast.LENGTH_SHORT).show();
    }
}
