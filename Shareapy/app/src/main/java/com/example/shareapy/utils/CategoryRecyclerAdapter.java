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
import java.util.List;

public class CategoryRecyclerAdapter  extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Category> categories;

    public CategoryRecyclerAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    };
    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName =itemView.findViewById(R.id.tv_home_category_item);
            ivImage = itemView.findViewById(R.id.iv_home_category_item);
        }
    }
    @Override
    public CategoryRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_categories_items,parent,false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String name =categories.get(position).getName();
        int imID = categories.get(position).getPictID();

        holder.tvName.setText(name);
        holder.ivImage.setImageResource(imID);
    }
}
