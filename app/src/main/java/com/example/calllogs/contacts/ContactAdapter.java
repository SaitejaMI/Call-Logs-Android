package com.example.calllogs.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calllogs.Objects.Contact;
import com.example.calllogs.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewAdapter>  {
    ArrayList<Contact> filteredContacts;
    private final ItemClickListener clickListener;

    public ContactAdapter(ArrayList<Contact> filteredContacts,ItemClickListener clickListener) {
        this.filteredContacts = filteredContacts;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ContactViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_design,parent,false);
        return new ContactViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewAdapter holder,final int position) {
        holder.contact_name.setText(filteredContacts.get(position).getName());
        holder.itemView.setOnClickListener(view -> clickListener.onItemClick(filteredContacts.get(position),position));
    }

    @Override
    public int getItemCount() {
        return filteredContacts.size();
    }

    public static class ContactViewAdapter extends RecyclerView.ViewHolder {

        TextView contact_name;
        public ContactViewAdapter(@NonNull View itemView) {
            super(itemView);
            contact_name = itemView.findViewById(R.id.contact_name);

        }
    }
    public interface ItemClickListener{
        void onItemClick(Contact contact,int position);
    }
}
