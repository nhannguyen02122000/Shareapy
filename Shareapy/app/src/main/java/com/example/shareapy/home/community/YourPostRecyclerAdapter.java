package com.example.shareapy.home.community;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.models.ActivityInfo;
import com.example.shareapy.models.Post;
import com.example.shareapy.utils.CategoryActivity;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YourPostRecyclerAdapter extends RecyclerView.Adapter<YourPostRecyclerAdapter.MyViewHolder> {
    private Activity activity;
    private ArrayList<Post> posts;
    FirebaseAuth mFirebaseAuth = UserSignUp.getInstance().getmFireBaseAuth();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public YourPostRecyclerAdapter(){}

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setItem(ArrayList<Post> posts){
        this.posts=  posts;
    }

    public YourPostRecyclerAdapter(Context context, ArrayList<Post> posts) {
        this.posts = posts;
    };

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvTime,tvNoAppreciate,tvNoSupport;
        private Button btnEdit;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.txtCardTitle);
            tvTime = itemView.findViewById(R.id.txtCardTime);
            tvNoAppreciate = itemView.findViewById(R.id.tvNoAppreciate);
            tvNoSupport=itemView.findViewById(R.id.tvNoSupport);
            btnEdit = itemView.findViewById(R.id.btnCardEdit);
        }
    }

    @Override
    public YourPostRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_my_post,parent,false);
        YourPostRecyclerAdapter.MyViewHolder vh = new YourPostRecyclerAdapter.MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final YourPostRecyclerAdapter.MyViewHolder holder, final int position) {
        String title = posts.get(position).getContent();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a dd-MM-yyyy");
        Date time = posts.get(position).getTime();
        String date = simpleDateFormat.format(time);
        String noSupport = Integer.toString(posts.get(position).getSupportList().size());
        String noAppreciate = Integer.toString(posts.get(position).getAppreciateList().size());
        String postID = posts.get(position).getId();

        FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
        final String uid = fbUser.getUid();


        holder.tvNoSupport.setText(noSupport);
        holder.tvNoAppreciate.setText(noAppreciate);
        holder.tvTitle.setText(title);
        holder.tvTime.setText(date);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunityEditPostActivity.editPost=posts.get(position);
                CommunityEditPostActivity.position=position;
                activity.startActivity(new Intent(activity,CommunityEditPostActivity.class));
//                db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
//                                String name = document.getData().get("name").toString().trim();
//                                CommunityEditPostActivity.username = name;
//                                CommunityEditPostActivity.position=position;
//                                activity.startActivity(new Intent(activity,CommunityEditPostActivity.class));
//                            }
//                        }
//                    }
//                });

            }
        });
    }
}
