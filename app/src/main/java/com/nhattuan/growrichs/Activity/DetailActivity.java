package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nhattuan.growrichs.Adapter.ImageAdapter;
import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.helper.GoalsHelper;
import com.nhattuan.growrichs.helper.SessionManager;
import com.nhattuan.growrichs.model.ObjGoals;
import com.nhattuan.growrichs.ultil.GifSizeFilter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import static com.nhattuan.growrichs.Contants.Contants.REQUEST_CODE_CHOOSE;

public class DetailActivity extends AppCompatActivity implements ImageAdapter.UriAdapterListener {
    private RecyclerView mImageRecylerView;
    private Button mUploadButton;
    private List<Uri> mUriList;
    private ImageAdapter mImageAdapter;
    private Button mCancelButton, mOkButton;
    private TextView edit_goal;
    private ObjGoals goals;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        sessionManager = new SessionManager(this);
        goals = sessionManager.getGOAL();

        mImageRecylerView = findViewById(R.id.image_recylerview);
        mImageRecylerView.setLayoutManager(new GridLayoutManager(this, 3));
        mUriList = new ArrayList<>();
        mImageAdapter = new ImageAdapter(mUriList, this, this);
        mImageRecylerView.setAdapter(mImageAdapter);

        edit_goal = findViewById(R.id.edit_goal);
        mUploadButton = findViewById(R.id.upload_bt);
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(DetailActivity.this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(100)
                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .theme(R.style.MyGallery)
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });


        mCancelButton = findViewById(R.id.btn_cancel);
        mOkButton = findViewById(R.id.btn_ok);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goals != null) UpdateGoal();
                onBackPressed();
            }
        });
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goals != null) UpdateGoal();
                else
                    Toast.makeText(DetailActivity.this, "Please enter your goal!", Toast.LENGTH_SHORT).show();
            }
        });

        if (goals != null) {
            if (goals.getmTilte() != null) {
                edit_goal.setText(goals.getmTilte());
            }
            if (goals.getmImages() != null && goals.getmImages().length > 0) {
                for (int i = 0; i < goals.getmImages().length; i++) {
                    mUriList.add(Uri.parse(goals.getmImages()[i]));
                    mImageAdapter.ChangeList(mUriList);
                }
            }
        }

    }

    private void UpdateGoal() {
        if (mUriList.size() > 0) {
            String[] images = new String[mUriList.size()];
            for (int i = 0; i < mUriList.size(); i++) {
                images[i] = mUriList.get(i).toString();
            }
            goals.setmImages(images);
        } else {
            goals.setmImages(null);
        }
        sessionManager.setGOAL(goals);
        if (sessionManager.getGOAL() != null) {
            Toast.makeText(this, "Updated goal!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(DetailActivity.this, Activity11.class);
                    startActivity(mainIntent);
                }
            }, 2000);
        }
    }

    @Override
    public void onClick(Uri uri) {

    }

    @Override
    public boolean onLongClick(Uri uri, View v) {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mUriList.addAll(Matisse.obtainResult(data));
            mImageAdapter.ChangeList(mUriList);
        }
    }
}
