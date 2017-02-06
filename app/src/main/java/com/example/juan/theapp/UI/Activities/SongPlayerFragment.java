package com.example.juan.theapp.UI.Activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.theapp.Domain.Exceptions.MediaPlayerException;
import com.example.juan.theapp.R;
import com.example.juan.theapp.Services.MusicService;

import java.io.File;

public class SongPlayerFragment extends MyFragment implements MusicService.MusicServiceListener {

    private TextView songName;
    private SeekBar progressBar;
    private MediaPlayer mediaPlayer;
    private CountDownTimer timer;
    private final static int refreshTime = 300;
    private boolean bound;
    private MusicService mService;
    private ImageView playStopButton;
    private ImageView nextButton;
    private ImageView previusButton;
    private boolean wasPlaying;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            mService = binder.getService();
            mService.setOnCompletionListener(SongPlayerFragment.this);
            bound = true;
            mediaPlayer = mService.getMediaPlayer();
            if (!mediaPlayer.isPlaying()) {
                if (!mListener.checkPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    onError(new MediaPlayerException(MediaPlayerException.ErrorType.NO_PERMISSION));
                    return;
                }
                File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                try {
                    mService.prepare(sdCard);
                }
                catch (MediaPlayerException e) {
                    onError(e);
                    return;
                }
            }
            else playStopButton.setImageResource(R.mipmap.ic_pause);
            songName.setText(mService.getPlayingSong().getName());
            setTimer();
            if (mediaPlayer.isPlaying()) timer.start();
            progressBar.setMax(mediaPlayer.getDuration());
            progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (mediaPlayer.isPlaying()) {
                        pauseSong();
                        wasPlaying = true;
                    }
                    else wasPlaying = false;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                    if (wasPlaying) {
                        setTimer();
                        startSong();
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    private void setTimer() {
        timer = new CountDownTimer(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition(), refreshTime) {
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
        getActivity().setTitle(R.string.song_player);

        songName = (TextView) rootView.findViewById(R.id.songName);
        progressBar = (SeekBar) rootView.findViewById(R.id.songProgess);
        playStopButton = (ImageView) rootView.findViewById(R.id.playPause);
        previusButton = (ImageView) rootView.findViewById(R.id.prevSong);
        nextButton = (ImageView) rootView.findViewById(R.id.nextSon);

        playStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    pauseSong();
                    ((ImageView) v).setImageResource(R.mipmap.ic_play);
                } else {
                    startSong();
                    ((ImageView) v).setImageResource(R.mipmap.ic_pause);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mService.nextSong();
                } catch (MediaPlayerException e) {
                    onError(e);
                }
            }
        });

        previusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mService.previousSong();
                } catch (MediaPlayerException e) {
                    onError(e);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getContext(), MusicService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    void unBind() {
        timer.cancel();
        mediaPlayer.release();
        mService.removeListener();
        getActivity().unbindService(mConnection);
        bound = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bound) {
            if (timer != null) timer.cancel();
            Intent intent = new Intent(getContext(), MusicService.class);
            if (mediaPlayer.isPlaying()) {
                mService.startForeground();
                getActivity().startService(intent);
            }
            else {
                getActivity().stopService(intent);
            }
            mService.removeListener();
            getActivity().unbindService(mConnection);
            bound = false;
        }
    }

    @Override
    public void onNewSong(File song) {
        songName.setText(song.getName());
        timer.cancel();
        setTimer();
        if (mediaPlayer.isPlaying()) timer.start();
        progressBar.setMax(mediaPlayer.getDuration());
        progressBar.setProgress(0);
    }

    @Override
    public void onTracksFinished() {
        try {
            mService.resetAndPrepare();
            progressBar.setMax(mediaPlayer.getDuration());
            progressBar.setProgress(0);
            timer.cancel();
            setTimer();
            songName.setText(mService.getPlayingSong().getName());
            playStopButton.setImageResource(R.mipmap.ic_play);
        }
        catch (MediaPlayerException e) {
            onError(e);
        }
    }

    private void onError(MediaPlayerException exception) {
        if (timer != null) timer.cancel();
        progressBar.setProgress(0);
        playStopButton.setImageResource(R.mipmap.ic_play);
        switch (exception.getType()) {
            case READ:
                exception.printStackTrace();
                Toast.makeText(getContext(), "Error while loading a song", Toast.LENGTH_LONG).show();
                try {
                    mService.resetAndPrepare();
                    progressBar.setMax(mediaPlayer.getDuration());
                    setTimer();
                    return;
                } catch (MediaPlayerException ignored) {
                }
                break;
            case NO_SONGS:
                Toast.makeText(getContext(), "No songs found!", Toast.LENGTH_LONG).show();
                break;
            case NO_PERMISSION:
                Toast.makeText(getContext(), "Permission to read songs denied!", Toast.LENGTH_LONG).show();
                break;
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        playStopButton.setOnClickListener(listener);
        nextButton.setOnClickListener(listener);
        previusButton.setOnClickListener(listener);
    }
}
