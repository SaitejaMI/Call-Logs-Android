package com.example.calllogs.recent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calllogs.Objects.Call;
import com.example.calllogs.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewAdapter> {
    ArrayList<Call> filteredCalls;
    public RecentAdapter(ArrayList<Call> filteredCalls) {
        this.filteredCalls= filteredCalls;
    }

    @NonNull
    @Override
    public RecentViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_design,parent,
                false);
        return new RecentViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewAdapter holder, int position) {
        holder.img.setImageResource(getCallTypeImage(filteredCalls.get(position).getType()));
        holder.call_date.setText(dateString(filteredCalls.get(position).getDate()));
        holder.call_duration.setText(callTime(filteredCalls.get(position).getDuration()));
        holder.call_name.setText(filteredCalls.get(position).getName());
        holder.call_number.setText(filteredCalls.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return filteredCalls.size();
    }

    public class RecentViewAdapter extends RecyclerView.ViewHolder {

        ImageView img;
        TextView call_name, call_duration, call_date, call_number;

        public RecentViewAdapter(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.call_type);
            call_duration = itemView.findViewById(R.id.call_duration);
            call_date = itemView.findViewById(R.id.call_date);
            call_name = itemView.findViewById(R.id.call_name);
            call_number = itemView.findViewById(R.id.call_number);
        }
    }

    public int getCallTypeImage(int callType){
        switch (callType){
            case 0:
                return R.drawable.ic_baseline_call_made_24;
            case 1:
                return R.drawable.ic_baseline_call_received_24;
            case 2:
                return R.drawable.ic_baseline_call_missed_outgoing_24;
        }
        return R.drawable.call_black_48dp;
    }

    public String dateString(long callDayTime){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(callDayTime);
    }

    public String callTime(int callDuration){
        if(callDuration>3600){
            return callDuration/3600+" hours "+(callDuration%3600)/60+" mins "+callDuration%60+" secs";
        }
        if(callDuration<3600 && callDuration>60) {
            return callDuration/60+" mins "+callDuration%60+" secs";
        }
        return callDuration+" secs";
    }
}
