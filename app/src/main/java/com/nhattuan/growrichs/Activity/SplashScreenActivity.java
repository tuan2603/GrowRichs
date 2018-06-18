package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.SessionManager;

import static com.nhattuan.growrichs.Contants.Contants.SPLASH_DISPLAY_LENGTH;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sessionManager = new SessionManager(this);

//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        final Intent mainIntent = new Intent(SplashScreenActivity.this, SecretActivity.class);
//        if (getIntent().getExtras() != null) {
//            mainIntent.putExtra("FullMessage", getIntent().getExtras().getString("FullMessage"));
//            mainIntent.putExtra("linkURL", getIntent().getExtras().getString("linkURL"));
//        }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        startActivity(mainIntent);
                        SplashScreenActivity.this.finish();
                        //overridePendingTransition(0,0);

                }
            }, SPLASH_DISPLAY_LENGTH);


    }

}
