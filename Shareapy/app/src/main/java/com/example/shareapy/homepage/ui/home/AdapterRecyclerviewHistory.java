package com.example.shareapy.homepage.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.models.ActivityInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerviewHistory extends RecyclerView.Adapter<AdapterRecyclerviewHistory.ActivityViewHolder> {
    private List<ActivityInfo> listActivity = new ArrayList<>();
    private boolean isHistory;
    String pattern = "h:mm a dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    AdapterRecyclerviewHistory(boolean isHistory){
        this.isHistory = isHistory;
    }
    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_activity, parent,false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        ActivityInfo info = listActivity.get(position);
        holder.txtActivityCardTitle.setText(info.getTitle());
        holder.txtActivityCardTime.setText(simpleDateFormat.format(info.getTime()));
        holder.rtbActivityCardRate.setRating(info.getRate());
        if (!isHistory) {
            holder.rtbActivityCardRate.setVisibility(View.GONE);
        }
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
        TextView txtActivityCardTitle, txtActivityCardTime;
        RatingBar rtbActivityCardRate;
        ActivityViewHolder(View itemView) {
            super(itemView);
            txtActivityCardTitle = itemView.findViewById(R.id.txtActivityCardTitle);
            txtActivityCardTime = itemView.findViewById(R.id.txtActivityCardTime);
            rtbActivityCardRate = itemView.findViewById(R.id.rtbActivityCardRate);
        }
    }
}
