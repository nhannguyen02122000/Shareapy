package com.example.shareapy.home.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;


public class AdapterViewpagerCommunity extends FragmentStateAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    public AdapterViewpagerCommunity(Fragment fragment){
        super (fragment);
    }

    public AdapterViewpagerCommunity(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = mFragmentList.get(position);
        return fragment;
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    public String getTitle(int position){
        switch (position){
            case 0: return "YOUR POSTS";
            case 1: return "COMMUNITY";
            default: return "Undefined";
        }
    }
}
