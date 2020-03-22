package com.example.shareapy.homepage.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.models.ActivityInfo;
import com.example.shareapy.utils.SavedInstance;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerviewCurrent extends RecyclerView.Adapter<AdapterRecyclerviewCurrent.ActivityViewHolder> {
    private List<ActivityInfo> listActivity = new ArrayList<>();
    private Activity activity;
    AdapterRecyclerviewCurrent(){
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_activity_current, parent,false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        final ActivityInfo info = listActivity.get(position);
        holder.txtCurrentTitle.setText(info.getTitle());
        holder.txtCurrentCounselor.setText(info.getCounselor());
        holder.btnCurrentJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = info.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                if (!url.contains("https")){
                    url="https://"+url;
                }
                i.setData(Uri.parse(url));
                SavedInstance.homeActivity.startActivity(i);
            }
        });

    }

    public void setItem(List<ActivityInfo> listActivityNew){
        this.listActivity.clear();
        this.listActivity.addAll(listActivityNew);
    }

    @Override
    public int getItemCount() {
        return listActivity.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView txtCurrentTitle, txtCurrentCounselor;
        Button btnCurrentJoin;
        ActivityViewHolder(View itemView) {
            super(itemView);
            txtCurrentTitle = itemView.findViewById(R.id.txtCurrentTitle);
            txtCurrentCounselor = itemView.findViewById(R.id.txtCurrentCounselor);
            btnCurrentJoin = itemView.findViewById(R.id.btnCurrentJoin);
        }
    }
}
