package com.example.salma.salmaalarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by salma on 30/09/17.
 */

public class RingtonePlayingService extends Service {
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("in the service", "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        // If the service stopped it will not automatically restart.
        mediaPlayer = MediaPlayer.create(this, R.raw.alarma_musical);
        mediaPlayer.start();


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Ringtone service is stopped", Toast.LENGTH_SHORT).show();
    }
}
