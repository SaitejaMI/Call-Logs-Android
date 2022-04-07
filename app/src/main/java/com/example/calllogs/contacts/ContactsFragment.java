package com.example.calllogs.contacts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.calllogs.MainActivity;
import com.example.calllogs.Objects.Contact;
import com.example.calllogs.R;
import com.example.calllogs.individual.IndividualFragment;
import com.example.calllogs.remove.RemovedFragment;

import java.util.ArrayList;

public class ContactsFragment extends Fragment implements ContactAdapter.ItemClickListener {

    private final ArrayList<Contact> filteredContacts = new ArrayList<>();

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.contactRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ContactAdapter(getContacts(), this) {

        });
        Button button= view.findViewById(R.id.removed_contacts);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Fragment fragment=new RemovedFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment)
                        .commit();
            }

        }
        );

        return view;
    }

    public ArrayList<Contact> getContacts(){
        filteredContacts.clear();
        filteredContacts.addAll(MainActivity.contacts);
        return filteredContacts;
    }

    public Contact getContactByName(String name){
        for (int i=0;i<filteredContacts.size();i++) {
            if(filteredContacts.get(i).getName().equals(name))
                return filteredContacts.get(i);
        }
        return filteredContacts.get(1);
    }
    @Override
    public void onItemClick(Contact contact,int position) {
        Fragment fragment = new IndividualFragment(contact,position);
        requireActivity().getSupportFragmentManager().beginTransaction()
        .replace(R.id.frameLayout,fragment)
        .commit();
    }
}