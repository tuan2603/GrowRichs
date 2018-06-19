package com.nhattuan.growrichs.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;

import com.nhattuan.growrichs.Contants.ListLogan;
import com.nhattuan.growrichs.Contants.ListSong;
import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjPick;
import com.nhattuan.growrichs.services.AlarmReceiver;
import com.nhattuan.growrichs.services.AlarmReceiverMorning;
import com.nhattuan.growrichs.services.MusicService;
import com.google.gson.Gson;

import java.util.Calendar;

public class ExperimentActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {
    private static final String TAG = "ExperimentActivity";

    private MusicService musicSrv;

    private boolean musicBound = false;


    private TextView tv_slogan;
    private TextView tv_timer;
    private Button btn_start_visual;
    private Button btn_snooze;
    ObjPick objPick;


    private Intent playIntent;


    private static int dayofMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private static int timeHour;
    private static int timeMinute;
    private AlarmManager alarmManager;
    private PendingIntent morningIntent;
    private PendingIntent eveningIntent;
    private AudioManager audioManager = null;
    private boolean VEVENING = false;
    private boolean MORNING = false;
    private Vibrator v;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        unlockScreen();
        initializeUI();

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        onClick();
    }

    private void onClick() {
        btn_snooze.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if (objPick != null) {
                    cancelAlarm();
                    if (objPick.getmSnooze() > 0) {
                        if (VEVENING) {
                            Intent evening = new Intent(getApplicationContext(), AlarmReceiver.class);
                            eveningIntent = PendingIntent.getBroadcast(
                                    getApplicationContext(), 1, evening,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            setAlarm(eveningIntent, objPick.getmSnooze());
                            objPick.setTimeHour(timeHour);
                            objPick.setTimeMinute(timeMinute);
                            new SessionManager(ExperimentActivity.this).setSleep(objPick);
                        } else if (MORNING) {
                            Intent morning = new Intent(getApplicationContext(), AlarmReceiverMorning.class);
                            morningIntent = PendingIntent.getBroadcast(
                                    getApplicationContext(), 2, morning,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            setAlarm(morningIntent, objPick.getmSnooze());
                            objPick.setTimeHour(timeHour);
                            objPick.setTimeMinute(timeMinute);
                            new SessionManager(ExperimentActivity.this).setMorning(objPick);
                        }
                    }
                }
                finish();
            }
        });

        btn_start_visual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent home = new Intent(ExperimentActivity.this, VisualizingActivity.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(home);
                        finish();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setAlarm(PendingIntent pendingIntent, int MINUTE) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        calendar.add(Calendar.MINUTE,MINUTE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        timeHour = calendar.get(Calendar.HOUR_OF_DAY);
        timeMinute  = calendar.get(Calendar.MINUTE);
    }

    private void unlockScreen() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void setTimePicker() {
        Calendar calendar = Calendar.getInstance();
        tv_timer.setTextColor(Color.BLACK);
        tv_timer.setText(
                new StringBuilder().append(pad(calendar.get(Calendar.HOUR_OF_DAY)))
                        .append(":").append(pad(calendar.get(Calendar.MINUTE))));
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            //get service
            musicSrv = binder.getService();
            //pass list

            musicSrv.setList(ListSong.getListSong());

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
        playIntent = new Intent(this, MusicService.class);
        this.bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
        if (getIntent() != null) {
            VEVENING = getIntent().getBooleanExtra("VEVENING", false);
            MORNING = getIntent().getBooleanExtra("MORNING", false);
            if (VEVENING) {
                objPick = new SessionManager(this).getSleep();
                Intent evening = new Intent(getApplicationContext(), AlarmReceiver.class);
                eveningIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 1, evening,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                tv_slogan.setText(ListLogan.getList().get(dayofMonth-1).getmEvening());
            }
            if (MORNING) {
                objPick = new SessionManager(this).getMorning();
                Intent morning = new Intent(getApplicationContext(), AlarmReceiverMorning.class);
                morningIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 2, morning,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                tv_slogan.setText(ListLogan.getList().get(dayofMonth-1).getmMorning());
            }
            if (objPick != null) {
                Log.d(TAG, "getSleep: " + new Gson().toJson(objPick));
                if (!TextUtils.isEmpty(objPick.getRingTone().trim())) {
                    if (objPick.getRingTone().trim().length() > 0) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int position = 0;
                                for (int i = 0; i < ListSong.getListSong().size(); i++) {
                                    if (ListSong.getListSong().get(i).getmName().equals(objPick.getRingTone().trim()))
                                        position = i;
                                }
                                musicSrv.setSong(position);
                                musicSrv.playSong();
                            }
                        }, 1000);

                    }

                    Log.d(TAG, "onCreate: " + objPick.getRingTone());
                }

                tv_slogan.setText(ListLogan.getList().get(dayofMonth).getmEvening());

                if (objPick.ismVibration()) {
                    final long[] pattern = {0,400,800,600,800,800,800,1000};
                    // Vibrate for 500 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        v.vibrate(VibrationEffect.createWaveform(pattern, 0));
                    } else {
                        //deprecated in API 26
                        v.vibrate(pattern,0);
                    }
                }

                if (objPick.getmSpeakers() >= 0)
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            objPick.getmSpeakers(), 0);

                timeHour = objPick.getTimeHour();
                timeMinute = objPick.getTimeMinute();

            }
        }
        setTimePicker();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (musicBound) {
            // Hủy giàng buộc kết nối với dịch vụ.
            this.unbindService(musicConnection);
            musicBound = false;
        }

        if (v != null) v.cancel();
    }


    private void cancelAlarm() {
        if (alarmManager != null) {
            if (eveningIntent != null) {
                alarmManager.cancel(eveningIntent);
                eveningIntent.cancel();
            }

            if (morningIntent != null) {
                alarmManager.cancel(morningIntent);
                morningIntent.cancel();
            }

            if (v != null) {
                v.cancel();
            }
            pause();
        }
    }

    @Override
    protected void onDestroy() {
        musicSrv = null;
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
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
        if (musicSrv == null)
            return 0;
        return musicSrv.getDur();
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


    private void initializeUI() {
        tv_timer = findViewById(R.id.tv_timer);
        tv_slogan = findViewById(R.id.tv_slogan);
        btn_start_visual = findViewById(R.id.btn_start_visual);
        btn_snooze = findViewById(R.id.btn_snooze);

    }

}
