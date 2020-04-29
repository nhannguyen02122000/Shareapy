package com.example.shareapy.home.community;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.models.ActivityInfo;
import com.example.shareapy.models.CurrentUser;
import com.example.shareapy.models.Post;
import com.example.shareapy.utils.SavedInstance;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterCommunityPost extends RecyclerView.Adapter<AdapterCommunityPost.ActivityViewHolder> {
    private ArrayList<Post> listPost = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AdapterCommunityPost(){
    }

    AdapterCommunityPost(ArrayList<Post> listPost){
        this.listPost = listPost;
    }

    public void setItem(ArrayList<Post> posts){

        this.listPost=  posts;
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a dd-MM-yyyy");
        Date time = post.getTime();
        String date = simpleDateFormat.format(time);

        holder.txtCmnPostTitle.setText(post.getContent());
        holder.txtCmnPostTime.setText(date);
        holder.txtUserName.setText(post.getUserName());

        int noSupport = post.getSupportList().size();
        int noAppreciate = post.getAppreciateList().size();
        ArrayList<String> supportList = post.getSupportList();
        ArrayList<String> appreciateList = post.getAppreciateList();

        if (post.getAppreciateList().contains(CurrentUser.userID))
        {
            holder.btnCommunityAppreciate.setChecked(true);
        }
        else
        {
            holder.btnCommunityAppreciate.setChecked(false);
        }
        if (post.getSupportList().contains(CurrentUser.userID))
        {
            holder.btnCommunitySupport.setChecked(true);
        }
        else
        {
            holder.btnCommunitySupport.setChecked(false);
        }

        holder.btnCommunitySupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnCommunitySupport.isChecked())
                {
                    //Thêm vào support
                    supportList.add(CurrentUser.userID);
                    Map<String,Object> data = new HashMap<>();
                    data.put("supportList",supportList);
                    db.collection("Posts").document(post.getId()).set(data, SetOptions.merge());
                }
                else
                {
                    //Bỏ khỏi support
                    supportList.remove(CurrentUser.userID);
                    Map<String,Object> data = new HashMap<>();
                    data.put("supportList",supportList);
                    db.collection("Posts").document(post.getId()).set(data, SetOptions.merge());
                }
            }
        });

        holder.btnCommunityAppreciate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnCommunityAppreciate.isChecked())
                {
                    //Thêm vào support
                    appreciateList.add(CurrentUser.userID);
                    Map<String,Object> data = new HashMap<>();
                    data.put("appreciateList",appreciateList);
                    db.collection("Posts").document(post.getId()).set(data, SetOptions.merge());
                }
                else
                {
                    //Bỏ khỏi support
                    appreciateList.remove(CurrentUser.userID);
                    Map<String,Object> data = new HashMap<>();
                    data.put("appreciateList",appreciateList);
                    db.collection("Posts").document(post.getId()).set(data, SetOptions.merge());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView txtCmnPostTitle, txtCmnPostTime,txtUserName;
        ToggleButton btnCommunitySupport, btnCommunityAppreciate;
        ActivityViewHolder(View itemView) {
            super(itemView);
            txtCmnPostTitle = itemView.findViewById(R.id.txtCmnPostTitle);
            txtCmnPostTime = itemView.findViewById(R.id.txtCmnPostTime);
            btnCommunitySupport = itemView.findViewById(R.id.btnCommunitySupport);
            btnCommunityAppreciate = itemView.findViewById(R.id.btnCommunityAppreciate);
            txtUserName = itemView.findViewById(R.id.tvUserName_post);
        }
    }
}
