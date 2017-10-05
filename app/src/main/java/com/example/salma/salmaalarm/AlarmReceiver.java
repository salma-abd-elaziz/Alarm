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
        // The user pressed on or off.
        String UserPressed = intent.getExtras().getString("extra");
        // To get the chosen ringtone from the spinner.
        int chosenRingtone = intent.getExtras().getInt("ringtone");

        Log.e("the key", UserPressed);
        Log.e("ringtone", String.valueOf(chosenRingtone));
        // Start the RingtoneService.
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        // Pass extras I received from mainActivity to RingtoneService.
        serviceIntent.putExtra("extra", UserPressed);
        serviceIntent.putExtra("ringtone", chosenRingtone);

        // Start the service.
        context.startService(serviceIntent);


    }
}
