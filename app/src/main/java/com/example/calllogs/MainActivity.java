package com.example.calllogs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, PackageManager.PERMISSION_GRANTED);

        t1 = findViewById(R.id.textView);
    }


    public void getCall(View view) {

        String output = "";

//        getting info of call logs
        Uri uricall = Uri.parse("content:/call_log/calls");

//           getting info of call logs
        Cursor cursorcall = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);


        String number;
        String name;
        String callDuration;
        int type;
        String dateString;
        String dir;
        cursorcall.moveToFirst();
        do {
            number = cursorcall.getString(cursorcall.getColumnIndex(CallLog.Calls.NUMBER));
            name = cursorcall.getString(cursorcall.getColumnIndex(CallLog.Calls.CACHED_NAME));
            int duration = cursorcall.getColumnIndex(CallLog.Calls.DURATION);
            int dates = cursorcall.getColumnIndex(CallLog.Calls.DATE);
            type = cursorcall.getColumnIndex(CallLog.Calls.TYPE);
            String callType = cursorcall.getString(type);
            String callDate = cursorcall.getString(dates);
            Date callDayTime = new Date(Long.valueOf(callDate));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm");
             dateString = formatter.format(callDayTime);
            callDuration = cursorcall.getString(duration);
             dir = null;
            int dircode = Integer.parseInt(callType);

            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "Missed";
                    break;

            }
            if (name == null) {
                name = "Empty";
            }


            output += "Number  :  " + number + "\n \n" +
                    "Name  :  " + name + "\n \n" +
                    "Duration  :  " + callDuration + "\n \n" +
                    "Date  :  " + dateString + "\n \n" +
                    "Type  :  " + dir + "\n \n" +
                    "-----------------------------------------------" + "\n";

        }
            while (cursorcall.moveToNext());

            t1.setText(output);


            Background_Worker bgworker = new Background_Worker(this);
            bgworker.execute("register", number, name, callDuration, dateString , dir);

        }
    }
