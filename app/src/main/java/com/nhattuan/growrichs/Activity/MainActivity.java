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

import com.daimajia.swipe.util.Attributes;
import com.nhattuan.growrichs.Adapter.GoalAdapter;
import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.GoalsHelper;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjGoals;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoalAdapter.GoalAdapterListener {

    private static final String TAG = "MainActivity";
    private RecyclerView mGoalRecylerView;
    private ImageButton mAddButton;
    private List<ObjGoals> mGoalList;
    private GoalAdapter mGoalAdapter;
    private Button btnNext;
    private Button btn_secret;
    private Button goal_item_tv;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        sessionManager.setOpenApp(true);

        mGoalRecylerView = findViewById(R.id.goal_list);
        btnNext = findViewById(R.id.btn_next);
        btn_secret = findViewById(R.id.btn_secret);
        goal_item_tv = findViewById(R.id.goal_item_tv);

        GoalsHelper goalsHelper = new GoalsHelper();
        if (goalsHelper.getObjGoalssCount() <= 0) {
            goal_item_tv.setVisibility(View.VISIBLE);
        } else {
            goal_item_tv.setVisibility(View.GONE);
        }

        goal_item_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DetailIntent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(DetailIntent);
            }
        });


        mGoalList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        // use a linear layout manager
        mGoalRecylerView.setLayoutManager(mLayoutManager);

        mGoalAdapter = new GoalAdapter(mGoalList, this, this);
        mGoalAdapter.setMode(Attributes.Mode.Single);
        mGoalRecylerView.setAdapter(mGoalAdapter);
        mGoalRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mAddButton = findViewById(R.id.btn_add);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DetailIntent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(DetailIntent);
            }
        });

        setButtonNext();

        String fullname = "";
        String urllink = "";
        if (getIntent() != null) {
            fullname = getIntent().getStringExtra("FullMessage");
            urllink = getIntent().getStringExtra("linkURL");
            if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(urllink)) {
                final String finalFullname = fullname;
                final String finalUrllink = urllink;
                fullname = "";
                urllink = "";
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, NotifyActivity.class);
                        intent.putExtra("FullMessage", finalFullname);
                        intent.putExtra("linkURL", finalUrllink);
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
        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollview));
        scrollview.setSmoothScrollingEnabled(false);
        scrollview.postDelayed(new Runnable() {

            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 2000);
        GoalsHelper goalsHelper = new GoalsHelper();
        if (goalsHelper.getObjGoalssCount() <= 0) {
            goal_item_tv.setVisibility(View.VISIBLE);
        } else {
            goal_item_tv.setVisibility(View.GONE);
        }

    }

    private void setButtonNext() {
        if (mGoalList != null && btnNext != null) {
            if (mGoalList.size() > 0) {
                btnNext.setVisibility(View.VISIBLE);
                mGoalRecylerView.setVisibility(View.VISIBLE);
            } else {
                btnNext.setVisibility(View.GONE);
                mGoalRecylerView.setVisibility(View.GONE);
            }

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentTimer = new Intent(MainActivity.this, AlarmNightActivity.class);
                    startActivity(intentTimer);
                }
            });
        }

        btn_secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecretActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoalList = new GoalsHelper().getAllObjGoalss();
        mGoalAdapter.ChangeList(mGoalList);
        setButtonNext();
        if (new GoalsHelper().getObjGoalssCount() <= 0) {
            goal_item_tv.setVisibility(View.VISIBLE);
        } else {
            goal_item_tv.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(ObjGoals goals) {
        Intent intentVisua = new Intent(MainActivity.this, VisualizingActivity.class);
        intentVisua.putExtra("GOAL", goals);
        startActivity(intentVisua);
    }

    @Override
    public boolean onLongClick(ObjGoals goals, View v) {
        return false;
    }

    @Override
    public void onDelete(ObjGoals goals) {
        new GoalsHelper().deleteGoal(goals);
        mGoalList = new GoalsHelper().getAllObjGoalss();
        mGoalAdapter.ChangeList(mGoalList);
        setButtonNext();
        if (new GoalsHelper().getObjGoalssCount() <= 0) {
            goal_item_tv.setVisibility(View.VISIBLE);
        } else {
            goal_item_tv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        sessionManager.setOpenApp(false);
        super.onDestroy();
    }
}
