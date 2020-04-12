package com.example.shareapy.home.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shareapy.R;
import com.example.shareapy.models.ActivityInfo;
import com.example.shareapy.models.CurrentUser;
import com.example.shareapy.models.Post;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class CommunityFragment extends Fragment {
    private TabLayout tlCommunity;
    private ViewPager2 vpCommunity;
    ProgressBar progressBar;
    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Post> publicPost = new ArrayList<>();
    ArrayList<Post> userPost = new ArrayList<>();
    FirebaseAuth mFirebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_community_fragment, container, false);
        tlCommunity = view.findViewById(R.id.tlCommunity);
        vpCommunity = view.findViewById(R.id.vpCommunity);
        progressBar = view.findViewById(R.id.pgb_Community);

        setupViewPager();
        getData();

        return view;
    }
    private void getData() {
        //Get username

        mFirebaseAuth = UserSignUp.getInstance().getmFireBaseAuth();
        FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
        String uid = fbUser.getUid();
        CommunityNewPostActivity.userName= CurrentUser.userName;
        CommunityEditPostActivity.username=CurrentUser.userName;

        //Get userPost only
        progressBar.setVisibility(View.VISIBLE);
        db.collection("Posts").whereEqualTo("userID",userId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    for (DocumentSnapshot document : task.getResult())
                    {
                        Post newPost = document.toObject(Post.class);
                        userPost.add(newPost);
                    }
                    Collections.sort(userPost, new Comparator<Post>() {
                        @Override
                        public int compare(Post newPost, Post t1) {
                            return (int)(t1.getTime().getTime()-newPost.getTime().getTime());
                        }
                    });
                    progressBar.setVisibility(View.INVISIBLE);
                    FragmentCommunityYourPosts.yourpostAdapter.setItem(userPost);
                    FragmentCommunityYourPosts.yourpostAdapter.notifyDataSetChanged();
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.e("hello", "Error getting documents: ", task.getException());
                }
            }
        });
        //Get Public only
        //Code goes here
    }
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
