package com.example.salma.salmaalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
// MainActivity takes to AlarmReceiver, then the AlarmReceiver will send signal to RingtoneService,
//why the MainActivity can't talk to the RingtoneService, because if it does the song will go on
//so the AlarmReceiver allow us to have the song playing after a certain amount of time.
public class MainActivity extends AppCompatActivity {

    //variables.
    private final String STATUS =  "Your alarm is ";
    AlarmManager alarmManager;
    TimePicker alarmTime;
    TextView status;
    // I don't know why we need this.
    Context context;
    PendingIntent pendingIntent;

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
        Button end = (Button) findViewById(R.id.end_alarm);

        // Create an intent for AlarmReceiver (will go to AlarmReceiver class).
        final Intent alarmReceiverIntent = new Intent(this.context, AlarmReceiver.class);

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

                //Adding extra string in the Intent to tell you started the alarm.
                alarmReceiverIntent.putExtra("extra", "Alarm On");
                //pending intent is the intent that delays the intent
                //until the specified calender time.
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                //setting the AlarmManager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                //after all that we need to set the manifest to allow broadcasting.  <receiver android:name=".AlarmReceiver"></receiver>
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarmText("Alarm Off!");
                //to cancel tha alarm. we still need to stop the ringtone.
                alarmManager.cancel(pendingIntent);

                //Adding extra string in the Intent to tell the clock you pressed off.
                alarmReceiverIntent.putExtra("extra", "Alarm Off");

                //Stop the ringTone.
                //This sends a signal to AlarmReceiver which will sends a signal to the RingTonePlayingService immediately.
                sendBroadcast(alarmReceiverIntent);

            }
        });
    }

    private void setAlarmText(String s) {
        status.setText(STATUS + s);
    }
}
