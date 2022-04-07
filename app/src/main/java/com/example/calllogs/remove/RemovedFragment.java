package com.example.calllogs.remove;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calllogs.MainActivity;
import com.example.calllogs.Objects.Contact;
import com.example.calllogs.R;
import com.example.calllogs.contacts.ContactsFragment;

import java.util.ArrayList;

public class RemovedFragment extends Fragment implements RemovedAdapter.ItemClickListener{

    private final ArrayList<Contact> removedList = new ArrayList<>();

    public RemovedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_removed, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.removedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RemovedAdapter(getRemoved(), (RemovedAdapter.ItemClickListener) this));
        return view;
    }

    private ArrayList<Contact> getRemoved() {
        removedList.clear();
        removedList.addAll(MainActivity.removedContact);
        return removedList;
    }

    @Override
    public void onItemClick(int position) {
        Fragment fragment = new ContactsFragment();
        MainActivity.contacts.add(MainActivity.removedContact.get(position));
        MainActivity.removedContact.remove(position);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,fragment)
                .commit();
    }
}