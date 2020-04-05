package com.example.shareapy.home.community;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.models.ActivityInfo;
import com.example.shareapy.models.Post;
import com.example.shareapy.utils.SavedInstance;

import java.util.ArrayList;
import java.util.List;

public class AdapterCommunityPost extends RecyclerView.Adapter<AdapterCommunityPost.ActivityViewHolder> {
    private ArrayList<Post> listPost = new ArrayList<>();
    AdapterCommunityPost(){
    }

    AdapterCommunityPost(ArrayList<Post> listPost){
        this.listPost = listPost;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_community_post, parent,false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        final Post post = listPost.get(position);
        holder.txtCmnPostTitle.setText(post.getContent());
    }


    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView txtCmnPostTitle, txtCmnPostTime;
        Button btnCommunitySupport, btnCommunityAppreciate;
        ActivityViewHolder(View itemView) {
            super(itemView);
            txtCmnPostTitle = itemView.findViewById(R.id.txtCmnPostTitle);
            txtCmnPostTime = itemView.findViewById(R.id.txtCmnPostTime);
            btnCommunitySupport = itemView.findViewById(R.id.btnCommunitySupport);
            btnCommunityAppreciate = itemView.findViewById(R.id.btnCommunityAppreciate);
        }
    }
}
