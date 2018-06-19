package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjGoals;
import com.nhattuan.growrichs.model.ObjPick;

public class Activity17 extends AppCompatActivity {
    private Button btn_next,btn_cancel, btn_go10 , btn_go11;
    private EditText edit_goal;

    private SessionManager sessionManager;
    private ObjPick Morning = null, Evening = null;
    private ObjGoals objGoals = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_17);

        sessionManager = new SessionManager(this);
        objGoals = sessionManager.getGOAL();

        btn_next = findViewById(R.id.btn_next);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_go10 = findViewById(R.id.btn_go10);
        btn_go11 = findViewById(R.id.btn_go11);
        edit_goal = findViewById(R.id.edit_goal);

        if (objGoals != null) {
            edit_goal.setText(objGoals.getmTilte());
        }

        //setup button
        btn_go11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go11 = new Intent(Activity17.this, Activity11.class);
                startActivity(go11);
            }
        });

        btn_go10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go10 = new Intent(Activity17.this, DetailActivity.class);
                startActivity(go10);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go10 = new Intent(Activity17.this, DetailActivity.class);
                startActivity(go10);
            }
        });
    }
}
