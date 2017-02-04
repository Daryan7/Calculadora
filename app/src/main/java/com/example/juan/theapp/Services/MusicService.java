package com.example.juan.theapp.Services;

import android.app.Notification;
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
import android.widget.Toast;

import com.example.juan.theapp.R;
import com.example.juan.theapp.UI.Activities.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer player;
    private File[] songs;
    private int currentSong;
    private final IBinder mBinder = new LocalBinder();
    private OnNextSongListener listener;

    public interface OnNextSongListener {
        void onNextSong(File song);
    }

    public MusicService() {
        currentSong = -1;
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public File getPlayingSong() {
        return songs[currentSong];
    }

    public void prepare(File file) throws IOException {
        songs = file.listFiles();
        currentSong = 0;
        while (currentSong < songs.length && songs[currentSong].isDirectory()) ++currentSong;
        if (currentSong  < songs.length) {
            player.reset();
            player.setDataSource(songs[currentSong].getAbsolutePath());
            player.prepare();
            return;
        }
        Toast.makeText(getApplicationContext(), "No music found!", Toast.LENGTH_LONG).show();
        Log.w("Media player service", "No songs found");
        stopSelf();
    }

    public void setOnCompletionListener(OnNextSongListener listener) {
        this.listener = listener;
    }

    public File nextSong() {
        File song = null;
        //noinspection StatementWithEmptyBody
        while (++currentSong < songs.length && songs[currentSong].isDirectory());
        if (currentSong < songs.length) {
            song = songs[currentSong];
            player.reset();
            try {
                player.setDataSource(song.getAbsolutePath());
                player.prepare();
                player.start();
                return song;
            }
            catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Can't play next song, sorry!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        stopSelf();
        return song;
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
        listener.onNextSong(nextSong());
    }
}
