package com.example.calllogs.recent;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import com.example.calllogs.Objects.Call;
import com.example.calllogs.R;
import com.example.calllogs.MainActivity;

import java.util.ArrayList;

public class RecentFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Call> filteredCalls= new ArrayList<Call>();
    private SearchView searchView;

    public RecentFragment() {
        // Required empty public constructor
    }

    public static RecentFragment newInstance(String param1, String param2) {
        RecentFragment fragment = new RecentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        recyclerView=view.findViewById(R.id.recentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new RecentAdapter(getCalls()));

        return view;
    }

    public ArrayList<Call> getCalls(){
        filteredCalls.clear();
        for(int i =0;i<MainActivity.calls.size();i++){
            filteredCalls.add(MainActivity.calls.get(i)) ;
        }
        return filteredCalls;
    }
}