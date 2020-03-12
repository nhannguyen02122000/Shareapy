package com.example.shareapy.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shareapy.R;
import com.example.shareapy.utils.UserSignUp;

public class SlideGenderFragment extends Fragment {
    Button btnPre,btnNextPart;
    UserSignUp user;
    RadioGroup rgGender;
    RadioButton rgbGender;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.signup_slide_gender,container,false);

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
                int checkedID = rgGender.getCheckedRadioButtonId();
                if (checkedID == -1)
                {
                    Toast.makeText(getContext(),"Please make your choice",Toast.LENGTH_SHORT).show();
                    return;
                }
                rgbGender = view.findViewById(checkedID);
                user.setGender(rgbGender.getText().toString().trim());

                Fragment toAge = new SlideAgeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,toAge)
                        .addToBackStack(toAge.getClass().getSimpleName()).commit();
            }
        });

        return view;
    }

    private void createView(View view) {
        btnPre = view.findViewById(R.id.btnPre_gender);
        btnNextPart = view.findViewById(R.id.btnNext_gender);
        rgGender = view.findViewById(R.id.rdgGender);
    }
}
