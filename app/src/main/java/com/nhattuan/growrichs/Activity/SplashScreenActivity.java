package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjPick;

import static com.nhattuan.growrichs.Contants.Contants.SPLASH_DISPLAY_LENGTH;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sessionManager = new SessionManager(this);
        if (sessionManager.getOpenApp()) {
            final Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            if (getIntent().getExtras() != null) {
                mainIntent.putExtra("FullMessage", getIntent().getExtras().getString("FullMessage"));
                mainIntent.putExtra("linkURL", getIntent().getExtras().getString("linkURL"));
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(mainIntent);
                    SplashScreenActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(SplashScreenActivity.this, SecretActivity.class);
                    startActivity(mainIntent);
                    SplashScreenActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }

    }

}
