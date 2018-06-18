package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.nhattuan.growrichs.ultil.ButtonPlus;

import java.util.ArrayList;
import java.util.List;

public class Activity11 extends AppCompatActivity implements GoalAdapter.GoalAdapterListener {
    private RecyclerView mGoalRecylerView;
    private ButtonPlus mAddButton;
    private List<ObjGoals> mGoalList;
    private GoalAdapter mGoalAdapter;
    private Button btnNext, mCancelButton;
    private SessionManager sessionManager;
    private ScrollView scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11);

        mGoalList = new ArrayList<>();
        sessionManager = new SessionManager(this);

        scrollview = findViewById(R.id.scrollview);
        mGoalRecylerView = findViewById(R.id.goal_list);
        btnNext = findViewById(R.id.btn_ok);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DetailIntent = new Intent(Activity11.this, AlarmActivity.class);
                startActivity(DetailIntent);
            }
        });
        mCancelButton = findViewById(R.id.btn_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


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
                Intent DetailIntent = new Intent(Activity11.this, AlarmActivity.class);
                startActivity(DetailIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (new GoalsHelper().getAllObjGoalss().size() > 0) {
            mGoalList = new GoalsHelper().getAllObjGoalss();
        } else {
            mGoalList.add(new ObjGoals(1, "I make 20% more of income in …", System.currentTimeMillis()));
        }


        mGoalAdapter.ChangeList(mGoalList);

        scrollview.setSmoothScrollingEnabled(false);
        scrollview.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (new GoalsHelper().getAllObjGoalss().size() > 0) {
            mGoalList = new GoalsHelper().getAllObjGoalss();
        } else {
            mGoalList.add(new ObjGoals(1, "I make 20% more of income in …", System.currentTimeMillis()));
        }
        mGoalAdapter.ChangeList(mGoalList);
    }


    @Override
    public void onClick(ObjGoals goals) {

    }

    @Override
    public boolean onLongClick(ObjGoals goals, View v) {
        return false;
    }

    @Override
    public void onDelete(ObjGoals goals) {

    }
}
