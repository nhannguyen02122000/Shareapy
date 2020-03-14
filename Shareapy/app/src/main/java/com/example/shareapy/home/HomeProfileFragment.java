package com.example.shareapy.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shareapy.R;
import com.example.shareapy.authentication.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeProfileFragment extends Fragment {
    Button btn_signOut;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_profile_fragment,container,false);

        btn_signOut = view.findViewById(R.id.btnLogOut_home_profile_frag);
        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent toLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(toLogin);
                getActivity().finish();
            }
        });
        return view;
    }

}
