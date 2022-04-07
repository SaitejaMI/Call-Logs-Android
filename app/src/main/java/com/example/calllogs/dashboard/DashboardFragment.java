package com.example.calllogs.dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calllogs.MainActivity;
import com.example.calllogs.Objects.Contact;
import com.example.calllogs.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    Contact contact=new Contact();
    ArrayList<Contact> contactArrayList = MainActivity.contacts;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        completeAnalysis();
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        int[] frequency ={contact.getNumberOfTotalCalls(),contact.getNumberOfMissedCalls(),contact.getNumberOfIncomingCalls(),contact.getNumberOfOutgoingCalls()};
        String[] freq ={"Total Calls","Missed Calls","Incoming Calls","Outgoing Calls"};
        int[] duration ={contact.getTotalDuration(),contact.getLongestDuration(),contact.getIncomingDuration(),contact.getOutgoingDuration()};
        String[] Dura ={"Total Duration","Longest Duration","Incoming Duration","Outgoing Duration"};

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (int i=0;i<frequency.length;i++){
            PieEntry pieEntry = new PieEntry(frequency[i],freq[i] );
            pieEntries.add(pieEntry);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Frequency");
        PieChart pieChartFrequency = view.findViewById(R.id.pie_chart_frequency_total);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChartFrequency.setData(new PieData(pieDataSet));
        pieChartFrequency.animateXY(5000,5000);
        pieChartFrequency.getDescription().setEnabled(false);

        ArrayList<PieEntry> pieEntries1 = new ArrayList<>();

        for(int i=0;i< duration.length;i++){
            PieEntry pieEntry = new PieEntry(duration[i],Dura[i]);
            pieEntries1.add(pieEntry);
        }
        PieDataSet pieDataSet1 = new PieDataSet(pieEntries1,"Frequency");
        PieChart pieChartDuration = view.findViewById(R.id.pie_chart_duration_total);
        pieDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChartDuration.setData(new PieData(pieDataSet1));
        pieChartDuration.animateXY(5000,5000);
        pieChartDuration.getDescription().setEnabled(false);
        return view;
    }

    public void completeAnalysis(){
        for(int i =0;i<contactArrayList.size();i++){
            contact.setNumberOfIncomingCalls(contact.getNumberOfIncomingCalls() + contactArrayList.get(i).getNumberOfIncomingCalls());
            contact.setNumberOfOutgoingCalls(contact.getNumberOfOutgoingCalls() + contactArrayList.get(i).getNumberOfOutgoingCalls());
            contact.setNumberOfMissedCalls(contact.getNumberOfMissedCalls()+contactArrayList.get(i).getNumberOfMissedCalls());
            contact.setLongestDuration(Math.max(contact.getLongestDuration(),contactArrayList.get(i).getTotalDuration()));
            contact.setOutgoingDuration(contact.getOutgoingDuration() + contactArrayList.get(i).getOutgoingDuration());
            contact.setIncomingDuration(contact.getIncomingDuration() + contactArrayList.get(i).getIncomingDuration());

        }
    }
}