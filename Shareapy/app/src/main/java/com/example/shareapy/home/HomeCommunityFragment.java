package com.example.shareapy.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shareapy.R;
import com.example.shareapy.homepage.ui.home.AdapterViewpagerActivity;
import com.example.shareapy.homepage.ui.home.AdapterViewpagerCommunity;
import com.example.shareapy.homepage.ui.home.FragmentActivityCurrent;
import com.example.shareapy.homepage.ui.home.FragmentActivityHistory;
import com.example.shareapy.homepage.ui.home.FragmentActivityUpcoming;
import com.example.shareapy.homepage.ui.home.FragmentCommunityCommunity;
import com.example.shareapy.homepage.ui.home.FragmentCommunityYourPosts;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeCommunityFragment extends Fragment {
    private TabLayout tlCommunity;
    private ViewPager2 vpCommunity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_community_fragment, container, false);
        tlCommunity = view.findViewById(R.id.tlCommunity);
        vpCommunity = view.findViewById(R.id.vpCommunity);

        setupViewPager();
        getData();

        return view;
    }
    private void getData() {}
    private void setupViewPager() {
        final AdapterViewpagerCommunity adapter = new AdapterViewpagerCommunity(getActivity());
        adapter.addFragment(new FragmentCommunityYourPosts());
        adapter.addFragment(new FragmentCommunityCommunity());
        vpCommunity.setAdapter(adapter);
        new TabLayoutMediator(tlCommunity, vpCommunity,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(adapter.getTitle(position));
                    }
                }
        ).attach();
    }
}
