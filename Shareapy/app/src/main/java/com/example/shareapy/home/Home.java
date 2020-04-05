package com.example.shareapy.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shareapy.R;
import com.example.shareapy.home.bookmark.HomeBookmarkFragment;
import com.example.shareapy.home.community.CommunityFragment;
import com.example.shareapy.home.activity.HomeActivityFragment;
import com.example.shareapy.home.homepage.HomeHomeFragment;
import com.example.shareapy.home.profile.HomeProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setView();
        //Set the first Frag
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,new HomeHomeFragment()).commit();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        bottomNav.setOnNavigationItemSelectedListener(this);
    }

    protected void setView()
    {
        bottomNav = findViewById(R.id.home_bottom_navigation);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFrag = null;
        switch (item.getItemId())
        {
            case R.id.home_nav_home:
                selectedFrag = new HomeHomeFragment();
                break;
            case R.id.home_nav_activity:
                selectedFrag = new HomeActivityFragment();
                break;
            case R.id.home_nav_profile:
                selectedFrag = new HomeProfileFragment();
                break;
            case R.id.home_nav_bookmark:
                selectedFrag = new HomeBookmarkFragment();
                break;
            case R.id.home_nav_community:
                selectedFrag = new CommunityFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,selectedFrag).commit();
        return true;
    }
}
