package com.example.juan.theapp.Services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.juan.theapp.R;
import com.example.juan.theapp.UI.Activities.BaseActivity;

import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer player;
    private final IBinder mBinder = new LocalBinder();

    public MusicService() {
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        Log.v("ma", "me destruyo");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void startForeground() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_error)
                        .setContentTitle("Playing something")
                        .setContentText("Texto de contenido");

        Intent resultIntent = new Intent(this, BaseActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(BaseActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        startForeground(1, mBuilder.build());
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public MediaPlayer getMediaPlayer() {
        return player;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.v("mediaplayer", "finished");
        player.reset();
    }
}
