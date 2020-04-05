package com.example.shareapy.utils;

import android.content.Context;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookmarkActivityRecyclerAdapter extends RecyclerView.Adapter<BookmarkActivityRecyclerAdapter.MyViewHolder> {
    private Context context;
    private Timestamp tsDateOfEvent;
    private ArrayList<CategoryActivity> categoryActivities;
    FirebaseAuth mFirebaseAuth = UserSignUp.getInstance().getmFireBaseAuth();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public BookmarkActivityRecyclerAdapter(Context context, ArrayList<CategoryActivity> categoryActivities,  Timestamp tsDateOfEvent) {
        this.context = context;
        this.categoryActivities = categoryActivities;
        this.tsDateOfEvent = tsDateOfEvent;
    };
    @Override
    public int getItemCount() {
        return categoryActivities == null ? 0 : categoryActivities.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvDate,tvSlot;
        private Button btnRegister;
        private ToggleButton tgbtnBookmark;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName =itemView.findViewById(R.id.tv_activeName_bookmark);
            tvDate = itemView.findViewById(R.id.tv_date_cate_bookmark);
            tvSlot = itemView.findViewById(R.id.tv_cate_slot_bookmark);
            btnRegister = itemView.findViewById(R.id.btn_register_category_bookmark);
            tgbtnBookmark = itemView.findViewById(R.id.tgbtn_bookmark_bookmark);
        }
    }
    @Override
    public BookmarkActivityRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_bookmark_fragment_items,parent,false);
        BookmarkActivityRecyclerAdapter.MyViewHolder vh = new BookmarkActivityRecyclerAdapter.MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final BookmarkActivityRecyclerAdapter.MyViewHolder holder, final int position) {
        String name =categoryActivities.get(position).getName();
        String date = categoryActivities.get(position).getDate();
        final String actiId = categoryActivities.get(position).getActiID();
        final ArrayList<String> registerList = categoryActivities.get(position).getRegisteredList();
        final int curPer = registerList.size();
        final int maxPer = 7;
        String slots = Integer.toString(curPer) + "/" + Integer.toString(maxPer);
        FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
        final String uid = fbUser.getUid();


        holder.tvName.setText(name);
        holder.tvDate.setText(date);
        holder.tvSlot.setText(slots);

        //Check date of event and cur date
        Date thisDate = new Date(System.currentTimeMillis());
        Timestamp curTimeStamp = new Timestamp(thisDate);
        if (tsDateOfEvent.getSeconds()<curTimeStamp.getSeconds())
        {
            holder.btnRegister.setVisibility(View.INVISIBLE);
        }

        //Check whether registered
        if (registerList.contains(uid))
        {
            holder.btnRegister.setText("Registered");
        }

        holder.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curPer==maxPer)
                {
                    Toast.makeText(context,"The event was full",Toast.LENGTH_SHORT).show();
                }
                else{
                    if (!registerList.contains(uid))
                    {
                        registerList.add(uid);
                        Map<String,Object> data = new HashMap<>();
                        data.put("registerList",registerList);
                        db.collection("ActivityInfos").document(actiId).set(data, SetOptions.merge());
                        holder.tvSlot.setText(Integer.toString(registerList.size()) + "/" + Integer.toString(maxPer));
                        holder.btnRegister.setText("Registered");
                        Toast.makeText(context,"Register Successful",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context,"You have registered",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.tgbtnBookmark.setChecked(true);
        final ArrayList<String>[] bookmark = new ArrayList[]{new ArrayList<>()};
        db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        bookmark[0] = (ArrayList<String>) document.getData().get("bookmark");
//                        if (bookmark[0].contains(actiId))
//                            holder.tgbtnBookmark.setChecked(true);
//                        else
//                            holder.tgbtnBookmark.setChecked(false);
                    }
                }
            }
        });

        holder.tgbtnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tgbtnBookmark.isChecked())
                {
                    //them vao favorit
                    bookmark[0].add(actiId);
                    Map<String,Object> data = new HashMap<>();
                    data.put("bookmark",bookmark[0]);
                    db.collection("Users").document(uid).set(data, SetOptions.merge());
                    Toast.makeText(context,"Added to your bookmark!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Loai ra khoi fav
                    categoryActivities.remove(categoryActivities.get(position));
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, categoryActivities.size());
                    bookmark[0].remove(actiId);
                    Map<String,Object> data = new HashMap<>();
                    data.put("bookmark",bookmark[0]);
                    db.collection("Users").document(uid).set(data, SetOptions.merge());
                    Toast.makeText(context,"Removed from your bookmark!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
