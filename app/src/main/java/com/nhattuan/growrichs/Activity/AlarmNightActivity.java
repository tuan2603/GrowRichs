package com.nhattuan.growrichs.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nhattuan.growrichs.Contants.ListSong;
import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjPick;
import com.nhattuan.growrichs.model.Song;
import com.nhattuan.growrichs.services.AlarmReceiver;
import com.nhattuan.growrichs.services.MusicService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class AlarmNightActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {


    private static final String TAG = "AlarmNightActivity";
    private TextView tv_title_pick;
    private TimePicker timePicker;

    private Button mCancelButton;
    private Button btn_next;
    private Button btn_ring_tone;
    private Button btn_snooze;
    private Switch swt_vibrator;


    private Calendar calendar;
    private SessionManager sessionManager;


    private static int timeHour;
    private static int timeMinute;

    private String mSleep = "Now Pick The Time Right Before You Go To Sleep…";
    private String mMorning = "Now Pick The Time You Get Up In the Morning...";
    private AudioManager audioManager = null;
    private SeekBar volumeSeekbar = null;
    private ImageButton imageButtonPlay;
    private TextView textViewRingtone;
    private Song songname = null;
    private MusicService musicSrv;
    private Intent playIntent = null;
    private boolean musicBound = false;

    private ArrayList<Song> songList;
    boolean play = false;

    Vibrator v;
    AlarmManager alarmManager;
    RelativeLayout snoozeLayout;

    private boolean done = false;

    int snooze = 0;

    public static AlarmNightActivity alarmNightActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_night);
        alarmNightActivity = this;
        songList = new ArrayList<Song>();

        sessionManager = new SessionManager(this);

        songList.addAll(ListSong.getListSong());

        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getmName().compareTo(b.getmName());
            }
        });
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mapWiget();


        tv_title_pick.setText(mSleep);

        final long[] pattern = {0, 2000, 1000};
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds


        volumeSeekbar = findViewById(R.id.seekbar_volume);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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

        swt_vibrator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b&&play==true)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(pattern, 0);
                    }
                }
                else v.cancel();
            }
        });

        imageButtonPlay = findViewById(R.id.img_btn_play);
        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!play && !musicSrv.isPng()) {
                    int position = -1;
                    if (songname != null) {
                        for (int i = 0; i < ListSong.getListSong().size(); i++) {
                            if (ListSong.getListSong().get(i).getmName().equals(songname.getmName()))
                                position = i;
                        }
                        musicSrv.setSong(position);
                        musicSrv.playSong();
                        imageButtonPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                        if (swt_vibrator.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(pattern, 0);
                            }
                        }
                        play = true;
                    }
                } else {
                    pause();
                    imageButtonPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    play = false;
                    v.cancel();
                }
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTimer = new Intent(AlarmNightActivity.this, AlarmActivity.class);
                startActivity(intentTimer);
                finish();
            }
        });
        //timePicker.setCurrentHour(timePicker.getCurrentHour());
        setTimePicker();
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                setEvening();
            }
        });
        setOnClick();
    }

    private void setEvening() {

        String am_pm = (timePicker.getCurrentHour() < 12) ? "AM" : "PM";
        if (am_pm.equals("AM")) timePicker.setCurrentHour(timePicker.getCurrentHour() + 12);

    }


    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
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
    protected void onResume() {
        super.onResume();
        {
            playIntent = new Intent(this, MusicService.class);
            this.bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void setOnClick() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!done) {
                    done = true;
                    alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    setAlarm(pendingIntent);

                } else {
                    Intent intentTimer = new Intent(AlarmNightActivity.this, AlarmActivity.class);
                    startActivity(intentTimer);
                    finish();
                }
            }
        });


        btn_ring_tone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentListSong = new Intent(AlarmNightActivity.this, ListSongActivity.class);
                startActivityForResult(intentListSong, 1);
            }
        });

    }


    private boolean checkSwicth() {
        if (swt_vibrator.isChecked()) {
            return true;
        }
        return false;
    }

    private void setAlarm(PendingIntent pendingIntent) {
        Calendar calendar = Calendar.getInstance();
        getTimePicker();
        int hourHH = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteHH = calendar.get(Calendar.MINUTE);
        if ( timeHour == hourHH && timeMinute < minuteHH ){
            calendar.add(Calendar.DATE, 1);
        }else
        if ( timeHour < hourHH ){
            calendar.add(Calendar.DATE, 1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


        String baihat = "";
        if (songname != null) baihat = songname.getmName();
        else
        {
            ObjPick objPick = new SessionManager(this).getSleep();
            if (objPick != null) baihat=objPick.getRingTone();
        }
        ObjPick objPick = new ObjPick(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), baihat, volumeSeekbar.getProgress(), checkSwicth(), snooze, calendar.getTimeInMillis());
        sessionManager.setSleep(objPick);
        btn_next.setText("Next");

        Log.d(TAG, "setTimePicker: "+ calendar.get(Calendar.DATE));
        Log.d(TAG, "setTimePicker: "+ timeHour);
        Log.d(TAG, "setTimePicker: "+ timeMinute);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Song result = (Song) data.getSerializableExtra("NAMESONG");
                if (result != null) {
                    textViewRingtone.setText(result.getmTitle());
                    songname = result;
                }
            }
        } else if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                Integer result = data.getIntExtra("SNOOZE", 0);
                if (result > 0) {
                    btn_snooze.setText(result + " minutes");
                } else btn_snooze.setText("Off");
                snooze = result;
            }
        }
        //
    }

    private void mapWiget() {
        calendar = Calendar.getInstance();
        timePicker = findViewById(R.id.timePicker);
        btn_next = findViewById(R.id.btn_next);
        set_timepicker_text_colour();
        tv_title_pick = findViewById(R.id.tv_title_pick);
        btn_ring_tone = findViewById(R.id.btn_ring_tone);
        swt_vibrator = findViewById(R.id.swt_vibrator);
        btn_snooze = findViewById(R.id.btn_snooze);
        textViewRingtone = findViewById(R.id.tv_ring_tone);
        mCancelButton = findViewById(R.id.cancle_bt);
        btn_ring_tone.setText("Default ringtone(" + ListSong.getListSong().size() + ")");
        snoozeLayout = findViewById(R.id.snooze_ly);
        if (!done) {
            btn_next.setText("done");
        }
        snoozeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent snoozeIntent = new Intent(AlarmNightActivity.this, SnoozeActivity.class);
                startActivityForResult(snoozeIntent, 99);
            }
        });
    }

    private void set_timepicker_text_colour() {
        Resources system = getResources().getSystem();
        int hour_numberpicker_id = system.getIdentifier("hour", "id", "android");
        int minute_numberpicker_id = system.getIdentifier("minute", "id", "android");
        int ampm_numberpicker_id = system.getIdentifier("amPm", "id", "android");

        NumberPicker hour_numberpicker = (NumberPicker) timePicker.findViewById(hour_numberpicker_id);
        NumberPicker minute_numberpicker = (NumberPicker) timePicker.findViewById(minute_numberpicker_id);
        NumberPicker ampm_numberpicker = (NumberPicker) timePicker.findViewById(ampm_numberpicker_id);

        set_numberpicker_text_colour(hour_numberpicker);
        set_numberpicker_text_colour(minute_numberpicker);
        set_numberpicker_text_colour(ampm_numberpicker);

        setDividerColor(hour_numberpicker, Color.WHITE);
        setDividerColor(minute_numberpicker, Color.WHITE);
        setDividerColor(ampm_numberpicker, Color.WHITE);
    }

    private void setDividerColor(NumberPicker picker, int color) {

        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void set_numberpicker_text_colour(NumberPicker number_picker) {
        final int count = number_picker.getChildCount();
        final int color = Color.WHITE;

        for (int i = 0; i < count; i++) {
            View child = number_picker.getChildAt(i);

            try {
                Field wheelpaint_field = number_picker.getClass().getDeclaredField("mSelectorWheelPaint");
                wheelpaint_field.setAccessible(true);

                ((Paint) wheelpaint_field.get(number_picker)).setColor(color);
                ((EditText) child).setTextColor(color);
                number_picker.invalidate();
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            } catch (IllegalArgumentException e) {
            }
        }
    }

    private void getTimePicker() {
        timeHour = timePicker.getCurrentHour();
        timeMinute = timePicker.getCurrentMinute();
    }

    private void setTimePicker() {
        calendar = Calendar.getInstance();
        timeHour = calendar.get(Calendar.HOUR_OF_DAY);
        timeMinute = calendar.get(Calendar.MINUTE);
        timePicker.setCurrentHour(timeHour);
        timePicker.setCurrentMinute(timeMinute);

        setEvening();

        ObjPick objPick = new SessionManager(this).getSleep();
        if (objPick != null) {
            Calendar creatTime = Calendar.getInstance();
            creatTime.setTimeInMillis(objPick.getmCreateTime());
            Log.d(TAG, "setTimePicker: "+ objPick.getTimeHour());
            Log.d(TAG, "setTimePicker: "+ objPick.getTimeMinute());
            if(creatTime.get(Calendar.DATE) == calendar.get(Calendar.DATE))
            {
                timePicker.setCurrentHour(objPick.getTimeHour());
                timePicker.setCurrentMinute(objPick.getTimeMinute());
            }
            if(objPick.getRingTone().length()>0)
            {
                for (int i=0;i<songList.size();i++)
                {
                    if(songList.get(i).getmName().equals(objPick.getRingTone()))
                        textViewRingtone.setText(songList.get(i).getmTitle());
                }
            }
            if(objPick.getmSnooze()>0)
                btn_snooze.setText(objPick.getmSnooze()+" minutes");
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        if (musicBound) {
            // Hủy giàng buộc kết nối với dịch vụ.
            this.unbindService(musicConnection);
            musicBound = false;
        }
        imageButtonPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        play = false;
        v.cancel();
    }


    @Override
    protected void onDestroy() {
        musicSrv = null;
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
        // if (musicSrv == null)
        // return 100;
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

}
