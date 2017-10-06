package com.example.salma.salmaalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PopActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private int startId;
    private boolean isRunning;
    private final int RANDOM = 0, MUSICAL = 1, COOL = 2, MORNING = 3, NICE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        Button end = (Button) findViewById(R.id.turnOffbtn);

        Log.e("in the pop", "yay");
        Log.e("in the service", "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

        // To find the intent which started the Activity.
        Intent intent = getIntent();

        // Get extras From the Alarm receiver.
        int chosenRingtone = intent.getExtras().getInt("ringtone");

        Log.e("in the service", String.valueOf(chosenRingtone));

        // specify the ringtone.
        if (chosenRingtone == RANDOM){
            chosenRingtone = getRandomNumber();
        }

        if (chosenRingtone == MUSICAL) {
            mediaPlayer = MediaPlayer.create(this, R.raw.alarma_musical);
            mediaPlayer.start();
        } else if (chosenRingtone == COOL) {
            mediaPlayer = MediaPlayer.create(this, R.raw.best_wake_up);
            mediaPlayer.start();
        } else if (chosenRingtone == MORNING) {
            mediaPlayer = MediaPlayer.create(this, R.raw.morning_alarm);
            mediaPlayer.start();
        } else if (chosenRingtone == NICE) {
            mediaPlayer = MediaPlayer.create(this, R.raw.nice_wake_up_alarm);
            mediaPlayer.start();
        }
        Log.e("play", "case 1");
        this.isRunning = true;
        this.startId = 0;

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("play", "end pressed ");
                mediaPlayer.stop();
                mediaPlayer.reset();

                // We need to tell the MainActivity that we stopped the alarm.
               onBackPressed();
            }
        });
    }

    private int getRandomNumber() {
        int n =  (int)(Math.random() * 4) + 1;
        Log.e("in service " , String.valueOf(n));
        return n;
    }
}
