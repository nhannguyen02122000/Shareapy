package com.example.shareapy.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.shareapy.R;

public class SignUpSlides extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_slides);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Fragment frag = new SlideUsernameFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.slide_container,frag).addToBackStack(frag.getClass().getSimpleName()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.slide_container,frag).commit();
    }
}
