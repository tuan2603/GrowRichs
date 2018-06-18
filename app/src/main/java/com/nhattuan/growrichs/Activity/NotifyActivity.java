package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.SessionManager;

public class NotifyActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private TextView FullMessage;
    private TextView linkURL;
    private Button loveit;

    String fullmesage;
    String linkurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifycation);
        sessionManager = new SessionManager(this);
        FullMessage = findViewById(R.id.FullMessage);
        linkURL = findViewById(R.id.linkURL);
        loveit = findViewById(R.id.loveit);

        loveit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getExtras() != null) {
            fullmesage = getIntent().getStringExtra("FullMessage");
            linkurl = getIntent().getStringExtra("linkURL");
            FullMessage.setText(fullmesage);
            final String htmlString="<u><b>"+linkurl+"</b></u>";
            linkURL.setText(Html.fromHtml(htmlString));
            if (htmlString.toLowerCase().contains("http://") || htmlString.toLowerCase().contains("https://") ) {
                linkURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkurl));
                        startActivity(browserIntent);
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
