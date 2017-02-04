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

import com.example.juan.theapp.Domain.Exceptions.MediaPlayerException;
import com.example.juan.theapp.R;
import com.example.juan.theapp.UI.Activities.BaseActivity;

import java.io.File;
import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer player;
    private File[] songs;
    private int currentSong;
    private final IBinder mBinder = new LocalBinder();
    private MusicServiceListener listener;

    public enum Error {
        READ_ERROR,
        NO_SONGS_ERROR
    }

    public interface MusicServiceListener {
        void onNextSong(File song);
        void onTracksFinished();
    }

    public MusicService() {
        currentSong = -1;
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        songs = null;
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public File getPlayingSong() {
        return songs[currentSong];
    }

    public void prepare(File file) throws MediaPlayerException {
        songs = file.listFiles();
        currentSong = 0;
        findNextSong();
    }

    public void resetAndPrepare() throws MediaPlayerException {
        currentSong = 0;
        findNextSong();
    }

    public void setOnCompletionListener(MusicServiceListener listener) {
        this.listener = listener;
    }

    private void findNextSong() throws MediaPlayerException {
        while (currentSong < songs.length && songs[currentSong].isDirectory()) ++currentSong;
        if (currentSong  < songs.length) {
            player.reset();
            try {
                player.setDataSource(songs[currentSong].getAbsolutePath());
                player.prepare();
            }
            catch (IOException e) {
                e.printStackTrace();
                throw new MediaPlayerException(MediaPlayerException.ErrorType.READ);
            }
            return;
        }
        throw new MediaPlayerException(MediaPlayerException.ErrorType.NO_SONGS);
    }

    @Override
    public void onDestroy() {
        player.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void startForeground() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_player)
                        .setContentTitle("Playing music");

        Intent resultIntent = new Intent(this, BaseActivity.class);
        resultIntent.putExtra("musicPlayer", true);
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

    public MediaPlayer getMediaPlayer() {
        return player;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        try {
            nextSong();
        }
        catch (MediaPlayerException e) {
            e.printStackTrace();
            stopSelf();
        }
    }

    public void nextSong() throws MediaPlayerException {
        boolean wasPlaying = player.isPlaying();
        findNextSong();
        if (wasPlaying) player.start();
        listener.onNextSong(getPlayingSong());
    }
}
