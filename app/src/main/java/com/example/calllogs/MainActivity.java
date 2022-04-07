package com.example.calllogs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.calllogs.Objects.Call;
import com.example.calllogs.Objects.Contact;
import com.example.calllogs.compare.CompareFragment;
import com.example.calllogs.contacts.ContactsFragment;
import com.example.calllogs.dashboard.DashboardFragment;
import com.example.calllogs.databinding.ActivityMainBinding;
import com.example.calllogs.notification.NotificationFragment;
import com.example.calllogs.recent.RecentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static ArrayList<Call> calls = new ArrayList<>();
    public static ArrayList<Contact> contacts = new ArrayList<>();
    public static ArrayList<Contact> removedContact = new ArrayList<>();
    public static ArrayList<String> removed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        getCall();
        //ReadRemovedContacts();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, PackageManager.PERMISSION_GRANTED);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new ContactsFragment()).commit();
    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            item -> {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.nav_compare:
                        fragment = new CompareFragment();
                        break;
                    case R.id.nav_contacts:
                        fragment = new ContactsFragment();
                        break;
                    case R.id.nav_dashboard:
                        fragment = new DashboardFragment();
                        break;
                    case R.id.nav_recent:
                        fragment = new RecentFragment();
                        break;
                    case R.id.nav_notification:
                        fragment = new NotificationFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();

                return true;
            };


    @SuppressLint("Range")
    public void getCall() {

        String output = "";

//        getting info of call logs
        Uri uricall = Uri.parse("content:/call_log/calls");

//           getting info of call logs
        Cursor cursorcall = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);

        String number;
        String name;
        int callDuration;
        String dateString;
        int dir=-1;
        int index=-1;
        String type=null;

        cursorcall.moveToFirst();

        do {

            Call call = new Call();
            Contact contact = new Contact();

            number = cursorcall.getString(cursorcall.getColumnIndex(CallLog.Calls.NUMBER));
            call.setNumber(number);

            name = cursorcall.getString(cursorcall.getColumnIndex(CallLog.Calls.CACHED_NAME));
            if(name != null)
                call.setName(name);
            else
                call.setName(number);

            callDuration = Integer.parseInt(cursorcall.getString(cursorcall.getColumnIndex(CallLog.Calls.DURATION)));
            call.setDuration(callDuration);

            int dates = cursorcall.getColumnIndex(CallLog.Calls.DATE);
            String callDate = cursorcall.getString(dates);
            long callDayTime = Long.parseLong(callDate);
            call.setDate(callDayTime);

            String callType = cursorcall.getString(cursorcall.getColumnIndex(CallLog.Calls.TYPE));
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = 0;
                    break;
                    case CallLog.Calls.INCOMING_TYPE:
                    dir = 1;
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = 2;
                    break;
            }
            call.setType(dir);


            calls.add(call);


            index = -1;
            for(int i =0;i<contacts.size();i++){
                    if(number.equals(contacts.get(i).getNumber())) {
                        index = i;

                        break;
                    }
                }

            if(index == -1) {
                contact.setNumber(call.getNumber());
                contact.setName(call.getName());
                contacts.add(contact);
                index = contacts.size()-1;
            }

            switch (dir){
                case 0:
                    contacts.get(index).setOutgoingDuration(contacts.get(index).getOutgoingDuration()+callDuration);
                    contacts.get(index).setNumberOfOutgoingCalls(contacts.get(index).getNumberOfOutgoingCalls()+1);
                    contacts.get(index).setLongestDuration(Math.max(contacts.get(index).getLongestDuration(),callDuration));
                    break;
                case 1:
                    contacts.get(index).setIncomingDuration(contacts.get(index).getIncomingDuration()+callDuration);
                    contacts.get(index).setNumberOfIncomingCalls(contacts.get(index).getNumberOfIncomingCalls()+1);
                    contacts.get(index).setLongestDuration(Math.max(contacts.get(index).getLongestDuration(),callDuration));
                case 2:
                    contacts.get(index).setNumberOfMissedCalls(contacts.get(index).getNumberOfMissedCalls()+1);
            }

        }
            while (cursorcall.moveToNext());
        Collections.sort(contacts,new NameComparator());


    }

    public String callType(int num){
        if(num == 0){
            return "Outgoing";
        }else if (num == 1){
            return "Incoming";
        }else if(num == 2){
            return "missed";
        }else {
            return "other";
        }
    }

    /*public void ReadRemovedContacts(){
        String path= "src/main/java/com/example/calllogs/RemovedContacts.csv";
        String line = "";
        try {
            Scanner scanner = new Scanner(new File(path));
            scanner.useDelimiter("[,\n]");
            while(scanner.hasNext()){
                removed.add(scanner.next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i<removed.size();i++){
            for(int j=0;j<contacts.size();i++){
                if(removed.get(i).equals(contacts.get(j).getNumber())){
                    removedContact.add(contacts.get(j));
                    contacts.remove(i);
                    j=0;
                    break;
                }
            }
        }
    }*/
    public ArrayList<Call> getFilteredCalls(int callType){
        if(calls.size() == 0){
            getCall();
        }
        ArrayList<Call> filteredCalls= new ArrayList<Call>();
        for(int i = 0;i<calls.size();i++){
            if(calls.get(i).getType() == callType)
                filteredCalls.add(calls.get(i));
        }
        return filteredCalls;
    }

    class NameComparator implements Comparator<Contact> {

        // override the compare() method
        public int compare(Contact s1, Contact s2)
        {
            return s1.getName().compareTo(s2.getName());
        }
    }
 }
