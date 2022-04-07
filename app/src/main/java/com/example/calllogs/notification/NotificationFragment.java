package com.example.calllogs.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.content.Context;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calllogs.R;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class NotificationFragment extends Fragment{
    Button selectTime,setAlarm,cancelAlarm;
    TextView selectedTime;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        selectedTime = view.findViewById(R.id.selectedTime);
        selectTime = view.findViewById(R.id.select_time);
        setAlarm = view.findViewById(R.id.set_alarm);
        cancelAlarm = view.findViewById(R.id.cancel_alarm);

        createNotificationChannel();
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }

        });

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { cancelAlarm(); }
        });
        return view;
    }

    private void cancelAlarm() {
        Intent intent = new Intent(getActivity().getApplicationContext(),AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),0,intent,0);
        if(alarmManager == null){
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getActivity(),"notification cancelled successfully",Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(),AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(getActivity(),"Notification set Successfully",Toast.LENGTH_SHORT).show();

    }

    private void showTimePicker() {
        picker= new MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(12)
        .setMinute(0)
        .setTitleText("Select Notification  Time")
        .build();

        picker.show(requireActivity().getSupportFragmentManager(),"CallLogAnalyser");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTime.setText(picker.getHour()+" : "+ picker.getMinute());

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);

            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "CallLogAnalyserNotification";
            String description = "Chanel for Apps Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("CallLogAnalyser",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager;
            notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}