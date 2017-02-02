package com.example.juan.theapp.UI.Activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.juan.theapp.R;
import com.example.juan.theapp.Services.MusicService;

import java.io.File;
import java.io.IOException;

public class SongPlayerFragment extends Fragment {

    private TextView songName;
    private SeekBar progressBar;
    private MediaPlayer mediaPlayer;
    private CountDownTimer timer;
    private final static int refreshTime = 300;
    private boolean bound;
    private MusicService mService;
    private Button playStopButton;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            mService = binder.getService();
            bound = true;

            File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

            mediaPlayer = mService.getMediaPlayer();
            if (!mediaPlayer.isPlaying()) {
                try {
                    mediaPlayer.setDataSource(sdCard.getAbsolutePath() + "/01 Dead Inside [Alta calidad].mp3");
                    songName.setText("Dead Inside]");
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else progressBar.setProgress(mediaPlayer.getCurrentPosition());
            setTimer();
            progressBar.setMax(mediaPlayer.getDuration());
            progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (mediaPlayer.isPlaying()) pauseSong();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                    setTimer();
                    startSong();
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    private void setTimer() {
        timer = new CountDownTimer(mediaPlayer.getDuration()-mediaPlayer.getCurrentPosition(), refreshTime) {
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
            }

            @Override
            public void onFinish() {
            }
        };
    }

    private void startSong() {
        mediaPlayer.start();
        timer.start();
    }

    private void pauseSong() {
        mediaPlayer.pause();
        timer.cancel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song_player, container, false);

        songName = (TextView) rootView.findViewById(R.id.songName);
        progressBar = (SeekBar) rootView.findViewById(R.id.songProgess);
        playStopButton = (Button) rootView.findViewById(R.id.playStopButton);

        playStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    pauseSong();
                }
                else startSong();
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = new Intent(getContext(), MusicService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bound) {
            Intent intent = new Intent(getContext(), MusicService.class);
            if (mediaPlayer.isPlaying()) {
                mService.startForeground();
                getActivity().startService(intent);
            }
            else {
                getActivity().stopService(intent);
            }
            getActivity().unbindService(mConnection);
            bound = false;
        }
    }
}