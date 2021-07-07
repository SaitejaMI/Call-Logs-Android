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

public class MainActivity extends AppCompatActivity {

    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG}, PackageManager.PERMISSION_GRANTED);

        t1 = findViewById(R.id.textView);
    }


    public void getCall(View view){

        String output = "";

        Uri uricall = Uri.parse("content:/call_log/calls");
//        Cursor cursorcall;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//
//        }

        Cursor cursorcall = getContentResolver().query(CallLog.Calls.CONTENT_URI , null , null , null , null);


        String number;
        String name;
        String duration;
        cursorcall.moveToFirst();
        do {
             number = cursorcall.getString(cursorcall.getColumnIndex(CallLog.Calls.NUMBER));
             name = cursorcall.getString(cursorcall.getColumnIndex(CallLog.Calls.CACHED_NAME));
             duration = cursorcall.getString(cursorcall.getColumnIndex(CallLog.Calls.DURATION));


           if (name == null)
           {
               name = "Empty";
           }


            output += "Number  :  " + number + "\n \n" +
                       "Name  :  " + name + "\n \n" +
                       "Duration  :  " + duration + "\n \n" +
                    "-----------------------------------------------" + "\n";

        }
        while (cursorcall.moveToNext());

        t1.setText(output);






        Background_Worker bgworker = new Background_Worker(this);
        bgworker.execute("register", number , name , duration);

    }
}