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

public class SlideAgeFragment extends Fragment {
    Button btnPre,btnNextPart;
    UserSignUp user;
    TextInputLayout tilAge;
    TextInputEditText tiedtAge;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.signup_slide_age,container,false);

        createView(view);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment toGender = new SlideGenderFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,toGender).commit();
            }
        });

        btnNextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = UserSignUp.getInstance();
                if (tiedtAge.getText().toString().trim().isEmpty())
                {
                    tilAge.setError(getString(R.string.error_emptyFiled));
                    requestFocus(tiedtAge);
                    return;
                }
                String age = tiedtAge.getText().toString().trim();
                user.setAge(Integer.parseInt(age));

                Fragment toReli = new SlideReligiousFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,toReli)
                        .commit();
            }
        });

        return view;
    }

    private void createView(View view)
    {
        btnPre = view.findViewById(R.id.btnPre_age);
        btnNextPart = view.findViewById(R.id.btnNext_age);
        tilAge = view.findViewById(R.id.tilAge_slideAge);
        tiedtAge=view.findViewById(R.id.tiedtAge_slideAge);

    }
    private void requestFocus(View view){
        if (view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
