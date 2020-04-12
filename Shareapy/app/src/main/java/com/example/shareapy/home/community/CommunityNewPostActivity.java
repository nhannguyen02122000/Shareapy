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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class CommunityNewPostActivity extends AppCompatActivity {
    public static String userName;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mFirebaseAuth;
    TextView tvUserName;
    EditText etPostContent;
    Switch swIsOnlyMe;
    String uid,postID;
    Button btnPost;
    ProgressBar progressBar;
    boolean isPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_new_post);
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

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btnPost.setVisibility(View.INVISIBLE);
                //Instantiate new Post obj
                Post newPost = new Post();
                newPost.setContent(etPostContent.getText().toString().trim());
                newPost.setTime(new Date());
                isPublic = !swIsOnlyMe.isChecked();
                newPost.setPublic(isPublic);
                newPost.setUserID(uid);

                //Upload to DB
                updateDB(mFirebaseAuth.getCurrentUser(),newPost);
            }
        });
    }

    protected void setView()
    {
        tvUserName = findViewById(R.id.tv_newpost_username);
        etPostContent = findViewById(R.id.edtPostContent);
        btnPost = findViewById(R.id.btnPostSubmit);
        swIsOnlyMe = findViewById(R.id.swtPostOnlyMe);
        progressBar = findViewById(R.id.pgb_newPost);

        tvUserName.setText(userName);
    }
    protected void updateDB(FirebaseUser user,Post newPost)
    {
        db.collection("Posts").add(newPost).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                newPost.setId(documentReference.getId());
                postID = documentReference.getId();
                documentReference.set(newPost);
                ArrayList<Post> posts = FragmentCommunityYourPosts.yourpostAdapter.getPosts();
                ArrayList<Post> forUpdate = new ArrayList<>();
                forUpdate.add(newPost);
                forUpdate.addAll(posts);
                FragmentCommunityYourPosts.yourpostAdapter.setItem(forUpdate);
                FragmentCommunityYourPosts.yourpostAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                btnPost.setVisibility(View.VISIBLE);
                CommunityNewPostActivity.super.onBackPressed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                btnPost.setVisibility(View.VISIBLE);
            }
        });



    }
}
