package com.example.juan.theapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MusicService extends Service {
    private MediaPlayer player;
    private final IBinder mBinder = new LocalBinder();

    public MusicService(){
        player = new MediaPlayer();
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public MediaPlayer getMediaPlayer() {
        return player;
    }
}
