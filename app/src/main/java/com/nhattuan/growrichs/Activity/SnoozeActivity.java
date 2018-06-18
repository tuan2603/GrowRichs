package com.nhattuan.growrichs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import com.nhattuan.growrichs.Adapter.SnoozeAdapter;
import com.nhattuan.growrichs.R;

public class SnoozeActivity extends AppCompatActivity {

    ListView snoozeListView;
    SnoozeAdapter snoozeAdapter;
    int position=-1;
    private Button mCancelButton;
    private Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        snoozeListView=findViewById(R.id.snooze_lv);
        snoozeAdapter=new SnoozeAdapter(this, new SnoozeAdapter.ItemClick() {
            @Override
            public void Click(int positon,View view) {
                RadioButton check=view.findViewById(R.id.check);
                check.setChecked(true);
                snoozeAdapter.chekc=positon;
                position=positon;
                snoozeAdapter.notifyDataSetChanged();
            }
        });
        snoozeListView.setAdapter(snoozeAdapter);
        snoozeListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        mCancelButton=findViewById(R.id.cancle_bt);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=0;
                onBackPressed();
            }
        });

        btn_next =  findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        switch (position)
        {
            case 0:returnIntent.putExtra("SNOOZE",0);break;
            case 1:returnIntent.putExtra("SNOOZE",1);break;
            case 2:returnIntent.putExtra("SNOOZE",3);break;
            case 3:returnIntent.putExtra("SNOOZE",5);break;
            case 4:returnIntent.putExtra("SNOOZE",10);break;
            case 5:returnIntent.putExtra("SNOOZE",15);break;
            default:returnIntent.putExtra("SNOOZE",0);break;
        }
        setResult(ListSongActivity.RESULT_OK,returnIntent);
        super.onBackPressed();

    }
}
