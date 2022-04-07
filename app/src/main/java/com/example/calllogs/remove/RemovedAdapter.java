package com.example.calllogs.remove;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calllogs.Objects.Contact;
import com.example.calllogs.R;

import java.util.ArrayList;

public class RemovedAdapter extends RecyclerView.Adapter<RemovedAdapter.RemovedViewAdapter> {
    ArrayList<Contact> removed;
    private final ItemClickListener clickListener;

    public RemovedAdapter(ArrayList<Contact> removed, ItemClickListener clickListener) {
        this.removed=removed;
        this.clickListener= clickListener;
    }

    @NonNull
    @Override
    public RemovedViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_design,parent,false);
        return new RemovedViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemovedViewAdapter holder,final int position) {
        holder.contact_name.setText(removed.get(position).getName());
        holder.itemView.setOnClickListener(view -> clickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return removed.size();
    }

    public static class RemovedViewAdapter extends RecyclerView.ViewHolder {
        TextView contact_name;

        public RemovedViewAdapter(View view) {
            super(view);
            contact_name = view.findViewById(R.id.contact_name);
        }
    }

    public interface ItemClickListener{
        void onItemClick(int position);
    }

}
