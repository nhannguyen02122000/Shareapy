package com.example.shareapy.authentication;

import android.os.Bundle;
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

public class SlideReligiousFragment extends Fragment {
    Button btnPre,btnNextPart;
    UserSignUp user;
    RadioGroup radioReli;
    RadioButton radioButReli;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.signup_slide_religious,container,false);
        createView(view);

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment toAge = new SlideAgeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,toAge).commit();
            }
        });
        btnNextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = UserSignUp.getInstance();
                int checkedID = radioReli.getCheckedRadioButtonId();
                if (checkedID == -1 )
                {
                    Toast.makeText(getContext(),"Please make your choice",Toast.LENGTH_SHORT).show();
                    return;
                }
                radioButReli = view.findViewById(checkedID);
                user.setReligious(radioButReli.getText().toString().trim());

                Fragment toSpirit = new SlideSpiritualFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,toSpirit)
                        .addToBackStack(toSpirit.getClass().getSimpleName()).commit();
            }
        });
        return view;
    }
    private void createView(View view) {
        btnPre = view.findViewById(R.id.btnPre_religious);
        btnNextPart = view.findViewById(R.id.btnNext_religious);
        radioReli = view.findViewById(R.id.rdgReligious);
    }
}