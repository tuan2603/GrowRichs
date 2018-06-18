package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nhattuan.growrichs.Adapter.IntroViewPagerAdapter;
import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.ultil.DepthPageTransformer;
import com.nhattuan.growrichs.ultil.MyViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    MyViewPager view_pager_intro;
    IntroViewPagerAdapter introViewPagerAdapter;

    LinearLayout LinearLayoutDot;
    private int dotscount = 0;
    private ImageView[] dotsImage;
    public int mIntRun = 1;
    private List<String> mImage;
    public static Integer position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        view_pager_intro = findViewById(R.id.view_pager_intro);
        LinearLayoutDot = findViewById(R.id.Linear_layout_dot);

        if (getIntent() != null){
            mImage = new ArrayList<>();
            mImage = getIntent().getStringArrayListExtra("IMAGES");
            position=getIntent().getIntExtra("POSITION",0);
            introViewPagerAdapter = new IntroViewPagerAdapter(IntroActivity.this, mImage);
            view_pager_intro.setAdapter(introViewPagerAdapter);
            view_pager_intro.setCurrentItem(position);
        }


        dotscount = introViewPagerAdapter.getCount();
        dotsImage = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++){
            dotsImage[i] = new ImageView(IntroActivity.this);
            dotsImage[i].setImageDrawable(ContextCompat.getDrawable(IntroActivity.this,R.drawable.ic_radio_button_unchecked_yellowlemon_24dp));
            LinearLayout.LayoutParams params = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            LinearLayoutDot.addView(dotsImage[i],params);
        }
        dotsImage[0].setImageDrawable(ContextCompat.getDrawable(IntroActivity.this,R.drawable.ic_radio_button_checked_yellowlemon_24dp));

        view_pager_intro.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++){
                    dotsImage[i].setImageDrawable(ContextCompat.getDrawable(IntroActivity.this,R.drawable.ic_radio_button_unchecked_yellowlemon_24dp));
                }
                dotsImage[position].setImageDrawable(ContextCompat.getDrawable(IntroActivity.this,R.drawable.ic_radio_button_checked_yellowlemon_24dp));
                introViewPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //Timer timer = new Timer();
        //timer.scheduleAtFixedRate(new MyTimeStack(),2000,5000);
        view_pager_intro.setPageTransformer(true, new DepthPageTransformer());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("POSITION",view_pager_intro.getCurrentItem());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    public class MyTimeStack extends TimerTask {

        @Override
        public void run() {
            IntroActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mIntRun < dotscount){
                        view_pager_intro.setCurrentItem(mIntRun++);
                    }else
                    if (mIntRun >= dotscount){
                        mIntRun = 0;
                    }

                }
            });

        }
    }
}
