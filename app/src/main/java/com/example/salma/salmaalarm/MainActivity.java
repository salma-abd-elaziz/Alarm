package com.example.salma.salmaalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
// MainActivity takes to AlarmReceiver, then the AlarmReceiver will send signal to RingtoneService,
// why the MainActivity can't talk to the RingtoneService, because if it does the song will go on
// so the AlarmReceiver allow us to have the song playing after a certain amount of time.

// TODO when you set the alarm more than once and the time is not near it gose on thought.
// TODO make the receiver with a view and make the vibration and volume level options.
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // variables.
    private final String STATUS =  "Your alarm is ";
    private AlarmManager alarmManager;
    private TimePicker alarmTime;
    private TextView status;
    private boolean alarmIsOn;
    private int chosenRingTone;
    // I don't know why we need this.
    private Context context;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        // Initializing alarm manager.
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // Initializing Time picker.
        alarmTime = (TimePicker) findViewById(R.id.timePicker);
        // Initializing the status textView.
        status = (TextView) findViewById(R.id.status);
        // Initializing calender object.
        final Calendar calendar = Calendar.getInstance();

        Button start = (Button) findViewById(R.id.start_alarm);
        Button end = (Button) findViewById(R.id.end_alarm);

        // Creating the spinner.
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Setting up the array of the spinner and its layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ringtones, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // OnItemSelectedListener of the spinner.
        spinner.setOnItemSelectedListener(this);

        // Create an intent for AlarmReceiver (will go to AlarmReceiver class).
        final Intent alarmReceiverIntent = new Intent(this.context, AlarmReceiver.class);

       // Onclick listener.
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alarmIsOn){ // Alarm is off.
                    alarmIsOn = true;
                    String hr = "", min = "", str = "";
                    if (Build.VERSION.SDK_INT >= 23) {
                        // set the calender.
                        calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getHour());
                        hr = String.valueOf(alarmTime.getHour());
                    } else {
                        // set the calender.
                        calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getCurrentHour());
                        hr = String.valueOf(alarmTime.getCurrentHour());
                    }
                    if (Build.VERSION.SDK_INT >= 23) {
                        calendar.set(Calendar.MINUTE, alarmTime.getMinute());
                        min = String.valueOf(alarmTime.getMinute());
                    } else {
                        calendar.set(Calendar.MINUTE, alarmTime.getCurrentMinute());
                        min = String.valueOf(alarmTime.getCurrentMinute());
                    }
                    str = Integer.parseInt(hr) > 12 ? String.valueOf(Integer.parseInt(hr) - 12) + ":" + min +"Pm" : hr + ":" + min+  " Am";
                    setAlarmText("Alarm On " + str);

                    // Adding extra string in the Intent to tell you started the alarm.
                    alarmReceiverIntent.putExtra("extra", "Alarm On");
                    alarmReceiverIntent.putExtra("ringtone", chosenRingTone);

                    // pending intent is the intent that delays the intent
                    // until the specified calender time.
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // setting the AlarmManager
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    // after all that we need to set the manifest to allow broadcasting.  <receiver android:name=".AlarmReceiver"></receiver>

                } else {  // Alarm is on.
                    CharSequence text = "Alarm already set!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarmIsOn) { // Alarm is on.
                    alarmIsOn = false;
                    setAlarmText("Alarm Off!");
                    // to cancel tha alarm. we still need to stop the ringtone.
                    alarmManager.cancel(pendingIntent);

                    // Adding extra string in the Intent to tell the clock you pressed off.
                    alarmReceiverIntent.putExtra("extra", "Alarm Off");

                    // Adding the chosenRingtone to the intent because we access it in the receiver
                    // TODO maybe you can check in the receiver if ! null.
                    alarmReceiverIntent.putExtra("ringtone", chosenRingTone);

                    // Stop the ringTone.
                    // This sends a signal to AlarmReceiver which will sends a signal to the RingTonePlayingService immediately.
                    sendBroadcast(alarmReceiverIntent);
                } else {  // Alarm is off.
                    CharSequence text = "Alarm already off!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }

    private void setAlarmText(String s) {
        status.setText(STATUS + s);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // This will be called when the selected item change.
        chosenRingTone = (int)id; // This will be sent as extra to the Service.
       // chosenRingTone = parent.getItemIdAtPosition(position);
        Log.e("Ringtone mainActivity", String.valueOf(chosenRingTone));
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, "Ringtone mainActivity", duration);
        toast.show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
