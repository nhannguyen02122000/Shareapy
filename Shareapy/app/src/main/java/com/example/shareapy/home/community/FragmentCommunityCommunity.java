package com.example.shareapy.home.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.models.Post;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCommunityCommunity extends Fragment {
    private RecyclerView rcvCommunityPost;
    private AdapterCommunityPost adapterCommunityPost = new AdapterCommunityPost(Post.generateFakePost());
    public FragmentCommunityCommunity() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_community, null);
        rcvCommunityPost = view.findViewById(R.id.rcvCommunityPost);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rcvCommunityPost.setLayoutManager(llm);
        rcvCommunityPost.setAdapter(adapterCommunityPost);
        return view;
    }
}
