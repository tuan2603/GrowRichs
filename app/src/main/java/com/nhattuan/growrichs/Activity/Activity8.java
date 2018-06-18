package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nhattuan.growrichs.R;


public class Activity8 extends AppCompatActivity {

    private Button btn_done;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8);

        btn_done = findViewById(R.id.btn_done);
        btn_next = findViewById(R.id.btn_next);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Activity8.this, Activity9.class);
                startActivity(mainIntent);
            }
        });
    }
}
