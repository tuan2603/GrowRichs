package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjGoals;


public class Activity9 extends AppCompatActivity {
    private EditText mGoalEditText;
    private SessionManager sessionManager;
    private  ObjGoals goals;
    private String checkChange = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9);
        sessionManager = new SessionManager(this);
        sessionManager = new SessionManager(this);
        goals = new ObjGoals();
        if (sessionManager.getGOAL() != null) {
            goals = sessionManager.getGOAL();
        }

        mGoalEditText=findViewById(R.id.edit_goal);
        Button mDoneButton = findViewById(R.id.btn_done);

        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mGoalEditText.getText().toString();
                if (!TextUtils.isEmpty(title)){
                    AddGoal(title);
                } else {
                    Toast.makeText(Activity9.this, "Please enter your goal!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (goals != null) {
            if (!TextUtils.isEmpty(goals.getmTilte())) {
                checkChange = goals.getmTilte();
                mGoalEditText.setText(goals.getmTilte());
            }
        }
    }

    private void AddGoal(final String title) {
        goals.setmID(1);
        goals.setmTilte(title);
        if (!checkChange.equals(title)){
            sessionManager.setGOAL(goals);
            Toast.makeText(this, "Added goal!", Toast.LENGTH_SHORT).show();
        }
        if(sessionManager.getGOAL() != null)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(Activity9.this, DetailActivity.class);
                    startActivity(mainIntent);
                }
            }, 2000);
        } else {
            Toast.makeText(this, "Add goal fail!", Toast.LENGTH_SHORT).show();
        }
    }
}
