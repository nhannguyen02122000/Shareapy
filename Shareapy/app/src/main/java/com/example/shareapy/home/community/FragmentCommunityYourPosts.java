package com.example.shareapy.home.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shareapy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCommunityYourPosts extends Fragment {
//    public static AdapterRecyclerviewCurrent currentAdapter = new AdapterRecyclerviewCurrent();
//    RecyclerView rcvCurrent;
    FloatingActionButton fabNewPost;
    public FragmentCommunityYourPosts() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_your_posts, null);

        setView(view);

        fabNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CommunityNewPostActivity.class));
            }
        });
//        rcvCurrent = view.findViewById(R.id.rcvCurrent);
//        LinearLayoutManager llm = new LinearLayoutManager(getContext());
//        rcvCurrent.setLayoutManager(llm);
//        rcvCurrent.setAdapter(currentAdapter);
        return view;
    }

    private void setView(View view)
    {
        fabNewPost = view.findViewById(R.id.fab_newPost);
    }
}
