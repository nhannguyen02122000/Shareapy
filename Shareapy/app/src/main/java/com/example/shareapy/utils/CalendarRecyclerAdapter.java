package com.example.shareapy.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
// Calendar Recycler Adapter

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.MyViewHolder> {

    private ArrayList<CategoryActivity> categoryActivities;
    private Context context;
    private Fragment frag;
    FirebaseAuth mFirebaseAuth = UserSignUp.getInstance().getmFireBaseAuth();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CalendarRecyclerAdapter(Context context, ArrayList<CategoryActivity> categoryActivities, Fragment frag) {
        this.context = context;
        this.categoryActivities = categoryActivities;
        this.frag = frag;
    }
    @Override
    public int getItemCount() {
        return categoryActivities == null ? 0 : categoryActivities.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEventHeader,tvEventSlots,tvEventDate,tvEventDuration,tvExit,tvRegister;
        private ToggleButton tgbtnBookmark;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvEventDuration = itemView.findViewById(R.id.tv_event_duration);
            tvEventHeader = itemView.findViewById(R.id.tv_event_header);
            tvEventDate = itemView.findViewById(R.id.tv_event_date);
            tvEventSlots=itemView.findViewById(R.id.tv_event_slot);
            tvExit = itemView.findViewById(R.id.tv_event_exit);
            tvRegister = itemView.findViewById(R.id.tv_event_register);
            tgbtnBookmark = itemView.findViewById(R.id.tgbtn_event_bookmark);
        }
    }
    @Override
    public CalendarRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_calendar_event_items,parent,false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String header = categoryActivities.get(position).getName();
        final String actiID =categoryActivities.get(position).getActiID();
        String date = categoryActivities.get(position).getDate();
        String duration = "90 minutes";
        final ArrayList<String> registerList = categoryActivities.get(position).getRegisteredList();
        final int curPer = registerList.size();
        final int maxPer = 7;
        String slots = Integer.toString(curPer) + "/" + Integer.toString(maxPer);

        holder.tvEventDuration.setText(duration);
        holder.tvEventHeader.setText(header);
        holder.tvEventDate.setText(date);
        holder.tvEventSlots.setText(slots);

        holder.tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().remove(frag).commit();
                ((FragmentActivity)context).getSupportFragmentManager().popBackStack();
            }
        });
        holder.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curPer==maxPer)
                {
                    Toast.makeText(context,"The event was full",Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
                    final String uid = fbUser.getUid();
                    if (!registerList.contains(uid))
                    {
                        registerList.add(uid);
                        Map<String,Object> data = new HashMap<>();
                        data.put("registerList",registerList);
                        db.collection("ActivityInfos").document(actiID).set(data,SetOptions.merge());
                        holder.tvEventSlots.setText(Integer.toString(registerList.size()) + "/" + Integer.toString(maxPer));
                        Toast.makeText(context,"Register Successful",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context,"You have registered",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        FirebaseUser fbUser = mFirebaseAuth.getCurrentUser();
        final String uid = fbUser.getUid();
        final ArrayList<String>[] bookmark = new ArrayList[]{new ArrayList<>()};
        db.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        bookmark[0] = (ArrayList<String>) document.getData().get("bookmark");
                        if (bookmark[0].contains(actiID))
                            holder.tgbtnBookmark.setChecked(true);
                        else
                            holder.tgbtnBookmark.setChecked(false);
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
                    bookmark[0].add(actiID);
                    Map<String,Object> data = new HashMap<>();
                    data.put("bookmark",bookmark[0]);
                    db.collection("Users").document(uid).set(data, SetOptions.merge());
                    Toast.makeText(context,"Added to your bookmark!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Loai ra khoi fav
                    bookmark[0].remove(actiID);
                    Map<String,Object> data = new HashMap<>();
                    data.put("bookmark",bookmark[0]);
                    db.collection("Users").document(uid).set(data, SetOptions.merge());
                    Toast.makeText(context,"Removed from your bookmark!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
