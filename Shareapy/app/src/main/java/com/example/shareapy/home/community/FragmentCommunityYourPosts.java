package com.example.shareapy.home.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.models.Post;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCommunityYourPosts extends Fragment {
    RecyclerView rcvYourPost;
    FloatingActionButton fabNewPost;
    public static YourPostRecyclerAdapter yourpostAdapter = new YourPostRecyclerAdapter();


    public FragmentCommunityYourPosts() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_your_posts, null);

        setView(view);
        yourpostAdapter.setActivity(getActivity());
        fabNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CommunityNewPostActivity.class));
            }
        });
        rcvYourPost = view.findViewById(R.id.rcvCommunityYourPosts);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rcvYourPost.setLayoutManager(llm);
        rcvYourPost.setAdapter(yourpostAdapter);
        return view;
    }

    private void setView(View view)
    {
        fabNewPost = view.findViewById(R.id.fab_newPost);
    }
}
