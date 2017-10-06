package com.example.salma.salmaalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("in the receiver", "yayaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // Fetch extra from the Intent.
        // To get the chosen ringtone from the spinner.
        int chosenRingtone = intent.getExtras().getInt("ringtone");

        Log.e("ringtone", String.valueOf(chosenRingtone));

        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
        serviceIntent.putExtra("ringtone", String.valueOf(chosenRingtone));

        serviceIntent.setClassName("com.example.salma.salmaalarm", "com.example.salma.salmaalarm.PopActivity");
        serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(serviceIntent);


    }
}
