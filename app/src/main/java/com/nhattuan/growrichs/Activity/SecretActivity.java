package com.nhattuan.growrichs.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nhattuan.growrichs.Contants.ListSong;
import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.controller.MusicController;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.Song;
import com.nhattuan.growrichs.services.MusicServiceSecret;
import com.nhattuan.growrichs.ultil.Utilities;

import java.util.ArrayList;
import java.util.List;

public class SecretActivity extends AppCompatActivity
        implements MediaController.MediaPlayerControl {
    private static final String TAG = "SecretActivity";

    private MusicServiceSecret musicSrv;
    private Intent playIntent;
    private boolean musicBound = false;
    private MusicController controller;


    private TextView mTextSeekto;
    private SeekBar mSeekbarAudio;
    private AudioManager audioManager = null;
    private SeekBar volumeSeekbar = null;

    private Utilities utils;
    private List<Song> songList;


    private Button btn_done;
    private ImageButton btn_play;
    private boolean play = false;
    private SessionManager sessionManager;
    private boolean mUserIsSeeking = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermissionToRecordAudio();
        }
        songList = new ArrayList<>();
        songList.addAll(ListSong.getSongSecret());
        utils = new Utilities();
        setController();
        initializeUI();
        initializeSeekbar();
        SetOnClick();
        sessionManager = new SessionManager(this);
        if (sessionManager.getOpenApp()) {
            btn_done.setText("I’m Done With This!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToRecordAudio() {
        String[] permissions = new String[]{
                android.Manifest.permission.RECEIVE_BOOT_COMPLETED,
                android.Manifest.permission.WAKE_LOCK,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.VIBRATE,
        };
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    1987);
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == 1987) {
            int count = 0;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    count++;
                }
            }
            if (grantResults.length == count) {
            } else {
                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }

    }

    private void setController() {
        //set the controller up
        controller = new MusicController(this);

        controller.setMediaPlayer(this);
        //controller.setAnchorView(findViewById(R.id.song_list));
        controller.setEnabled(true);

    }


    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicServiceSecret.MusicBinder binder = (MusicServiceSecret.MusicBinder) service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        if (playIntent == null) {
            playIntent = new Intent(this, MusicServiceSecret.class);
            this.bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
        }
        SessionManager session = new SessionManager(this);

        int totalDuration = session.getTotal();
        int currentDuration = session.getCurrent();

        // Displaying Total Duration time
        //mTextDuration.setText("" + utils.milliSecondsToTimer(totalDuration));
        // Displaying time completed playing
        mTextSeekto.setText("" + utils.milliSecondsToTimer(currentDuration));

        // Updating progress bar
        int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
        //Log.d("Progress", ""+progress);
        mSeekbarAudio.setProgress(progress);


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: " + musicBound);
        if (musicBound) {
            if (isPlaying()) {
                SessionManager session = new SessionManager(SecretActivity.this);
                int totalDuration = getDuration();
                int currentDuration = getCurrentPosition();
                session.setTotal(totalDuration);
                session.setCurrent(currentDuration);
            }
            // Hủy giàng buộc kết nối với dịch vụ.
            musicSrv = null;
            this.unbindService(musicConnection);
            musicBound = false;
        }

        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }


    private void resetTimemSeekbarAudio() {
        mSeekbarAudio.setProgress(0);
        mSeekbarAudio.setMax(100);
        // Updating progress bar
        updateProgressBar();
    }


    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void pause() {
        musicSrv.pausePlayer();
    }

    @Override
    public int getDuration() {
        if (musicSrv != null) {
            return musicSrv.getDur();
        } else return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicSrv != null && musicBound && musicSrv.isPng()) {
            return musicSrv.getPosn();
        } else return 0;
    }


    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);

    }

    @Override
    public boolean isPlaying() {
        if (musicSrv != null && musicBound)
            return musicSrv.isPng();
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    //play next
    private void playNext() {
        musicSrv.playNext();
        controller.show(0);
    }

    //play previous
    private void playPrev() {
        musicSrv.playPrev();
        controller.show(0);
    }

    private void initializeUI() {
        mTextSeekto = findViewById(R.id.tv_seekto);
        mSeekbarAudio = findViewById(R.id.seekbar_audio);
        volumeSeekbar = findViewById(R.id.seekbar_volume);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        btn_done = findViewById(R.id.btn_done);
        btn_play = findViewById(R.id.btn_play);
        btn_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
    }

    private void SetOnClick() {
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManager.getOpenApp()) {
                    Intent mainIntent = new Intent(SecretActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                } else {
                    Intent mainIntent = new Intent(SecretActivity.this, Activity8.class);
                    startActivity(mainIntent);
                }

            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!play) {
                    if (musicSrv != null) {
                        musicSrv.setSong(0);
                        musicSrv.playSong();
                        final int currentPosition = getCurrentPosition();

                        SessionManager session = new SessionManager(SecretActivity.this);

                        final int totalDuration = session.getTotal();
                        final int currentDuration = session.getCurrent();

                        Log.d(TAG, "totalDuration: " + totalDuration);
                        Log.d(TAG, "currentDuration: " + currentDuration);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (currentDuration < totalDuration) {
                                            musicSrv.playSongCurent(currentPosition , currentDuration);
                                        }
                                        // update timer progress again
                                        updateProgressBar();
                                    }
                                },300);


                    }
                } else {
                    if (isPlaying()) {
                        SessionManager session = new SessionManager(SecretActivity.this);
                        int totalDuration = getDuration();
                        int currentDuration = getCurrentPosition();
                        session.setTotal(totalDuration);
                        session.setCurrent(currentDuration);

                        pause();
                    }
                }
            }
        });

        volumeSeekbar.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekbar.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));
        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }
        });
    }


    private void initializeSeekbar() {
        mSeekbarAudio.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int userSelectedPosition = 0;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = true;
                        new Handler().removeCallbacks(mUpdateTimeTask);
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            userSelectedPosition = progress;
                        }
                    }


                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = false;
                        seekTo(userSelectedPosition);
                        new Handler().removeCallbacks(mUpdateTimeTask);
                        int totalDuration = getDuration();
                        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

                        // forward or backward to certain seconds
                        seekTo(currentPosition);

                        // update timer progress again
                        updateProgressBar();
                    }
                });
    }

    public void updateProgressBar() {
        new Handler().postDelayed(mUpdateTimeTask, 100);
    }


    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (isPlaying()) {
                btn_play.setImageResource(R.drawable.ic_pause_black_24dp);
                play = true;
                long totalDuration = getDuration();
                long currentDuration = getCurrentPosition();
                Log.d(TAG, "run: "+currentDuration);

                // Displaying Total Duration time
                //mTextDuration.setText("" + utils.milliSecondsToTimer(totalDuration));
                // Displaying time completed playing
                mTextSeekto.setText("" + utils.milliSecondsToTimer(currentDuration));

                // Updating progress bar
                int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
                //Log.d("Progress", ""+progress);
                mSeekbarAudio.setProgress(progress);
                // Running this thread after 100 milliseconds
                new Handler().postDelayed(this, 100);
            } else {
                btn_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                play = false;
            }
        }
    };

}
