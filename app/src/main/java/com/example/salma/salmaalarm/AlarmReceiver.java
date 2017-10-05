package com.example.salma.salmaalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("in the receiver", "yayaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // Fetch extra String from the Intent.
        String getExtra = intent.getExtras().getString("extra");

        Log.e("the keyyyy", getExtra);



        // Start the RingtoneService.
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        // Pass extra string to mainActivity to RingtoneService.
        serviceIntent.putExtra("extra", getExtra);

        // Start the service.
        context.startService(serviceIntent);


    }
}
