package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nhattuan.growrichs.Adapter.OverviewPagerAdapter;
import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.config;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjGoals;
import com.nhattuan.growrichs.ultil.TextviewPlus;

import java.util.ArrayList;
import java.util.List;

public class VisualizingActivity extends AppCompatActivity {

    private static final String TAG =  "VisualizingActivity";
    private ObjGoals objGoal;
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private OverviewPagerAdapter overviewPagerAdapter;
    private List<String> listImages;

    private Button btn_secret;
    private Button btn_make_goal;
    private Button btn_close;
    private Button btn_findhere;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizing);
        sessionManager = new SessionManager(this);
        if (sessionManager.getGOAL() != null){
            objGoal = sessionManager.getGOAL();
            if(objGoal.getmImages()!=null)
            for (int i = 0; i < objGoal.getmImages().length; i++){
                Log.d(TAG, objGoal.getmImages()[i].toString());
            }


        }
        mapWidget();
        setViewPage();
        btn_secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSecret = new Intent(VisualizingActivity.this, SecretActivity.class);
                startActivity(intentSecret);
            }
        });
    }

    private void setViewPage() {
        if (objGoal != null) {
            if (objGoal.getmImages().length > 0) {
                for (int i = 0; i < objGoal.getmImages().length; i++) {
                    listImages.add(objGoal.getmImages()[i]);
                }
            }

            overviewPagerAdapter = new OverviewPagerAdapter(VisualizingActivity.this, listImages, new OverviewPagerAdapter.Click() {
                @Override
                public void Click(int position) {
                    if(listImages.size()>0) {
                        Intent introIntent = new Intent(VisualizingActivity.this, IntroActivity.class);
                        introIntent.putStringArrayListExtra("IMAGES", (ArrayList<String>) listImages);
                        introIntent.putExtra("POSITION", position);
                        startActivityForResult(introIntent, 999);
                    }
                }
            });

            viewPager.setAdapter(overviewPagerAdapter);

            dotscount = overviewPagerAdapter.getCount();
            dots = new ImageView[dotscount];
            if (dotscount > 1) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i] = new ImageView(VisualizingActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(VisualizingActivity.this.getApplicationContext(), R.drawable.ic_radio_button_unchecked_yellowlemon_24dp));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16, 16);
                    params.setMargins(3, 5, 3, 5);
                    sliderDotspanel.addView(dots[i], params);
                }
                dots[0].setImageDrawable(ContextCompat.getDrawable(VisualizingActivity.this.getApplicationContext(), R.drawable.ic_radio_button_checked_yellowlemon_24dp));
            }
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(VisualizingActivity.this.getApplicationContext(), R.drawable.ic_radio_button_unchecked_yellowlemon_24dp));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(VisualizingActivity.this.getApplicationContext(), R.drawable.ic_radio_button_checked_yellowlemon_24dp));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private void mapWidget() {
        viewPager = findViewById(R.id.viewPager);
        sliderDotspanel = findViewById(R.id.sliderDots);
        btn_secret = findViewById(R.id.btn_secret);
        btn_make_goal = findViewById(R.id.btn_make_goal);
        btn_close = findViewById(R.id.btn_close);
        btn_findhere = findViewById(R.id.btn_findhere);





        btn_findhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(config.MakeYourOwnGoalCard));
                startActivity(browserIntent);
            }
        });

        btn_make_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go11 = new Intent(VisualizingActivity.this, Activity11.class);
                startActivity(go11);
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        listImages = new ArrayList<>();


    }

}
