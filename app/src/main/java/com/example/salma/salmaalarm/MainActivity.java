package com.example.salma.salmaalarm;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    //variables.
    private final String STATUS =  "Your alarm is ";
    AlarmManager alarmManager;
    TimePicker alarmTime;
    TextView status;
    // I don't know why we need this.
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        //Initializing alarm manager.
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //Initializing Time picker.
        alarmTime = (TimePicker) findViewById(R.id.timePicker);
        //Initializing the status textView.
        status = (TextView) findViewById(R.id.status);
        //Initializing calender object.
        final Calendar calendar = Calendar.getInstance();

        Button start = (Button) findViewById(R.id.start_alarm);
        //Onclick listener.
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hr = "", min = "", str = "";
                if (Build.VERSION.SDK_INT >= 23 ) {
                    calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getHour());
                    hr = String.valueOf(alarmTime.getHour());
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getCurrentHour());
                    hr = String.valueOf(alarmTime.getCurrentHour());
                }
                if (Build.VERSION.SDK_INT >= 23 ) {
                    calendar.set(Calendar.MINUTE, alarmTime.getMinute());
                    min = String.valueOf(alarmTime.getMinute());
                } else {
                    calendar.set(Calendar.MINUTE, alarmTime.getCurrentMinute());
                    min = String.valueOf(alarmTime.getCurrentMinute());
                }


                str = Integer.parseInt(hr) > 12 ? String.valueOf(Integer.parseInt(hr) - 12) + ":" + min +"Pm" : hr + ":" + min+  " Am";
                setAlarmText("Alarm On " + str);
            }
        });

        Button end = (Button) findViewById(R.id.end_alarm);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarmText("Alarm Off!");
            }
        });
    }

    private void setAlarmText(String s) {
        status.setText(STATUS + s);
    }
}
