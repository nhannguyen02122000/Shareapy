package com.example.shareapy.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;

import java.util.List;
// EventRecyclerAdapter

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Events> events;
    private Context context;
    private Fragment frag;

    public RecyclerAdapter(Context context, List<Events> events, Fragment frag) {
        this.context = context;
        this.events = events;
        this.frag = frag;
    }
    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEventHeader,tvEventSlots,tvEventDate,tvEventDuration,tvExit;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvEventDuration = itemView.findViewById(R.id.tv_event_duration);
            tvEventHeader = itemView.findViewById(R.id.tv_event_header);
            tvEventDate = itemView.findViewById(R.id.tv_event_date);
            tvEventSlots=itemView.findViewById(R.id.tv_event_slot);
            tvExit = itemView.findViewById(R.id.tv_event_exit);
        }
    }
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_calendar_event_items,parent,false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String header = events.get(position).getHeader();
        String date = events.get(position).getDate();
        String duration = events.get(position).getDuration();
        String slots = events.get(position).getSlot();

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
    }

}
