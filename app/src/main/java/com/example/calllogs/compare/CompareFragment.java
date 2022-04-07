package com.example.calllogs.compare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calllogs.R;

public class CompareFragment extends Fragment {


    public CompareFragment() {
    }

    public static CompareFragment newInstance(String param1, String param2) {
        CompareFragment fragment = new CompareFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compare, container, false);
    }
}