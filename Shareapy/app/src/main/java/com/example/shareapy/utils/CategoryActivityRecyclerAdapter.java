package com.example.shareapy.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;

import java.util.ArrayList;

public class CategoryActivityRecyclerAdapter extends RecyclerView.Adapter<CategoryActivityRecyclerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CategoryActivity> categoryActivities;
    public CategoryActivityRecyclerAdapter(Context context, ArrayList<CategoryActivity> categoryActivities) {
        this.context = context;
        this.categoryActivities = categoryActivities;
    };
    @Override
    public int getItemCount() {
        return categoryActivities == null ? 0 : categoryActivities.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvDate;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName =itemView.findViewById(R.id.tv_activeName);
            tvDate = itemView.findViewById(R.id.tv_date_cate);
        }
    }
    @Override
    public CategoryActivityRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_family_fragment_items,parent,false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String name =categoryActivities.get(position).getName();
        String date = categoryActivities.get(position).getDate();

        holder.tvName.setText(name);
        holder.tvDate.setText(date);


    }
}
