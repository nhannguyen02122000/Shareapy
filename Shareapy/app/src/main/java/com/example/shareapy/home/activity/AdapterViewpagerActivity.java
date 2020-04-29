package com.example.shareapy.home.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;


public class AdapterViewpagerActivity extends FragmentStateAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    public AdapterViewpagerActivity(Fragment fragment){
        super (fragment);
    }

    public AdapterViewpagerActivity(@NonNull FragmentActivity fragmentActivity) {
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
            case 0: return "History";
            case 1: return "Ongoing";
            case 2: return "Upcoming";
            default: return "Undefined";
        }
    }
}
