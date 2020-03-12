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

public class SlideSpiritualFragment extends Fragment {
    Button btnPre,btnNextPart;
    UserSignUp user;
    RadioGroup radioSpi;
    RadioButton radioButSpi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.signup_slide_spiritual,container,false);
        createView(view);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment toReli = new SlideReligiousFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,toReli).commit();
            }
        });
        btnNextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = UserSignUp.getInstance();
                int checkedID = radioSpi.getCheckedRadioButtonId();
                if (checkedID == -1 )
                {
                    Toast.makeText(getContext(),"Please make your choice",Toast.LENGTH_SHORT).show();
                    return;
                }
                radioButSpi = view.findViewById(checkedID);
                user.setSpiritual(radioButSpi.getText().toString().trim());

                Fragment toTherapy = new SlideTherapyFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,toTherapy)
                        .addToBackStack(toTherapy.getClass().getSimpleName()).commit();
            }
        });
        return view;
    }
    private void createView(View view) {
        btnPre = view.findViewById(R.id.btnPre_spiritual);
        btnNextPart = view.findViewById(R.id.btnNext_spiritual);
        radioSpi = view.findViewById(R.id.rdgSpirit);
    }
}