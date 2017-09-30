package com.example.salma.salmaalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.util.Log;

/**
 * Created by salma on 30/09/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("in the receiver", "yayaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        //start the RingtoneService.
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
        //start the service.
        context.startService(serviceIntent);


    }
}
