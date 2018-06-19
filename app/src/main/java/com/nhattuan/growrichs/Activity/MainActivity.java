package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;
import com.nhattuan.growrichs.Adapter.GoalAdapter;
import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.GoalsHelper;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjGoals;
import com.nhattuan.growrichs.model.ObjPick;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnNext,btn_evening,btn_morning, btn_go9,btn_cancel;
    private TextView tv_time_morning, tv_time_ev;

    private SessionManager sessionManager;
    private ObjPick Morning = null, Evening = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);

        btn_go9 = findViewById(R.id.btn_go9);
        btn_cancel = findViewById(R.id.btn_cancel);
        btnNext = findViewById(R.id.btn_next);
        btn_evening = findViewById(R.id.btn_evening);
        btn_morning = findViewById(R.id.btn_morning);
        tv_time_morning = findViewById(R.id.tv_time_morning);
        tv_time_ev = findViewById(R.id.tv_time_ev);

        Morning = sessionManager.getMorning();
        Evening = sessionManager.getSleep();

        if(Morning != null) {
            String hour = Morning.getTimeHour() + "";
            String minute =Morning.getTimeMinute() + "";
            tv_time_morning.setText(hour + " : " + minute);
        }

        if(Evening != null) {
            String hour = Evening.getTimeHour() + "";
            String minute =Evening.getTimeMinute() + "";
            tv_time_ev.setText(hour + " : " + minute);
        }


//        setup button
        btn_go9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go9 = new Intent(MainActivity.this, Activity9.class);
                startActivity(go9);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go9 = new Intent(MainActivity.this, Activity9.class);
                startActivity(go9);
            }
        });

        btn_morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent morning = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(morning);
            }
        });

        btn_evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent evening = new Intent(MainActivity.this, AlarmNightActivity.class);
                startActivity(evening);
            }
        });


        if (getIntent() != null) {
            final String  fullname = getIntent().getStringExtra("FullMessage");
            final String urllink = getIntent().getStringExtra("linkURL");
            if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(urllink)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, NotifyActivity.class);
                        intent.putExtra("FullMessage", fullname);
                        intent.putExtra("linkURL", urllink);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }, 1000);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        sessionManager.setOpenApp(true);
        super.onDestroy();
    }
}
