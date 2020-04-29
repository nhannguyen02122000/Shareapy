package com.example.shareapy.home.community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.shareapy.R;
import com.example.shareapy.models.Post;
import com.example.shareapy.utils.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CommunityEditPostActivity extends AppCompatActivity {
    public static String username = "";
    public static int position = -1;
    public static Post editPost = new Post();
    ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mFirebaseAuth;
    TextView tvUserName;
    EditText etPostContent;
    Switch swIsOnlyMe;
    String uid,postID;
    Button btnRePost;
    boolean isPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_edit_post);
        setView();

        mFirebaseAuth = UserSignUp.getInstance().getmFireBaseAuth();
        FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
        uid = fbUser.getUid();

        //Set username
//        db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
//                        String name = document.getData().get("name").toString().trim();
//                        tvUserName.setText(name);
//                    }
//                }
//            }
//        });

        btnRePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btnRePost.setVisibility(View.INVISIBLE);

                editPost.setContent(etPostContent.getText().toString().trim());
                editPost.setTime(new Date());
                isPublic = !swIsOnlyMe.isChecked();
                editPost.setPublic(isPublic);


                //Upload to DB
                updateDB(mFirebaseAuth.getCurrentUser());
            }
        });
    }

    protected void setView()
    {
        tvUserName = findViewById(R.id.tv_editpost_username);
        etPostContent = findViewById(R.id.edtPostContent_editPost);
        btnRePost = findViewById(R.id.btnPostSubmit_editPost);
        swIsOnlyMe = findViewById(R.id.swtPostOnlyMe_editPost);
        progressBar = findViewById(R.id.pgb_editPost);

        tvUserName.setText(username);
        etPostContent.setText(editPost.getContent());
        swIsOnlyMe.setChecked(!editPost.isPublic());
    }
    protected void updateDB(FirebaseUser user)
    {
        HashMap<String,Object> updateData = new HashMap<>();
        updateData.put("content",editPost.getContent());
        updateData.put("public",editPost.isPublic());
        updateData.put("time",editPost.getTime());

        db.collection("Posts").document(editPost.getId()).update(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    ArrayList<Post> posts = FragmentCommunityYourPosts.yourpostAdapter.getPosts();
                    posts.remove(position);
                    ArrayList<Post> forUpdate = new ArrayList<>();
                    forUpdate.add(editPost);
                    forUpdate.addAll(posts);
                    FragmentCommunityYourPosts.yourpostAdapter.setItem(forUpdate);
                    FragmentCommunityYourPosts.yourpostAdapter.notifyDataSetChanged();
                    CommunityEditPostActivity.super.onBackPressed();
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    btnRePost.setVisibility(View.VISIBLE);
                }
            }
        });
//        db.collection("Posts").add(newPost).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                //Nho xoa post cu khoi list
//                newPost.setId(documentReference.getId());
//                postID = documentReference.getId();
//                documentReference.set(newPost);
//                ArrayList<Post> posts = FragmentCommunityYourPosts.yourpostAdapter.getPosts();
//                ArrayList<Post> forUpdate = new ArrayList<>();
//                forUpdate.add(newPost);
//                forUpdate.addAll(posts);
//                FragmentCommunityYourPosts.yourpostAdapter.setItem(forUpdate);
//                FragmentCommunityYourPosts.yourpostAdapter.notifyDataSetChanged();
//                CommunityEditPostActivity.super.onBackPressed();
//            }
//        });



    }
}
