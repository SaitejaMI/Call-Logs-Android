package com.example.calllogs.individual;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.calllogs.MainActivity;
import com.example.calllogs.Objects.Contact;
import com.example.calllogs.R;
import com.example.calllogs.remove.RemovedFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class IndividualFragment extends Fragment {

    private final Contact contact;
    private final int position;

    public IndividualFragment(Contact contact,int position) {
        this.contact=contact;
        this.position=position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_individual, container, false);
        TextView individualName = view.findViewById(R.id.individual_contact);
        individualName.setText(contact.getName());
        TextView individualNumber = view.findViewById(R.id.individual_number);
        individualNumber.setText(contact.getNumber());
        Button button = view.findViewById(R.id.removed_contacts);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MainActivity.removedContact.add(MainActivity.contacts.get(position));
                MainActivity.contacts.remove(position);

                Fragment fragment=new RemovedFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout,fragment)
                        .commit();
            }
        });

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
        PieChart pieChartFrequency = view.findViewById(R.id.pie_chart_frequency);
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
        PieChart pieChartDuration = view.findViewById(R.id.pie_chart_duration);
        pieDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChartDuration.setData(new PieData(pieDataSet1));
        pieChartDuration.animateXY(5000,5000);
        pieChartDuration.getDescription().setEnabled(false);

        return view;
    }
}