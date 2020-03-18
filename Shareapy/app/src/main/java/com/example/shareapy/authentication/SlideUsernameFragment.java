package com.example.shareapy.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shareapy.R;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SlideUsernameFragment extends Fragment {
    Button btnPre,btnNextPart;
    UserSignUp user;
    TextInputLayout tilUsername;
    TextInputEditText tiedtUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.signup_slide_username,container,false);

        createView(view);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , SignUpActivity.class));
                getActivity().finish();
            }
        });

        btnNextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = UserSignUp.getInstance();
                if (tiedtUsername.getText().toString().trim().isEmpty())
                {
                    tilUsername.setError(getString(R.string.error_emptyFiled));
                    requestFocus(tiedtUsername);
                    return;
                }
                String username = tiedtUsername.getText().toString().trim();
                user.setUserName(username);

                Fragment toGender = new SlideGenderFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,toGender)
                        .addToBackStack(toGender.getClass().getSimpleName()).commit();
            }
        });

        return view;
    }

    private void createView(View view)
    {
        btnPre = view.findViewById(R.id.btnPre_username);
        btnNextPart = view.findViewById(R.id.btnNext_username);
        tilUsername = view.findViewById(R.id.tilUsername_SlideUsername);
        tiedtUsername=view.findViewById(R.id.tiedtUsername_SlideUsername);

    }
    private void requestFocus(View view){
        if (view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
