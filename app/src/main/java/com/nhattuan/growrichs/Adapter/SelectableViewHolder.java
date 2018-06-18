package com.nhattuan.growrichs.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.model.SelectableItem;

public class SelectableViewHolder extends RecyclerView.ViewHolder {

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;
    private static final String TAG = "SelectableViewHolder";
    public CheckedTextView checkedTextView;
    public ImageButton btnPlay;
    public LinearLayout ln_song;
    private boolean checked = false;

    private boolean play = false;
    public SelectableItem mItem;
    public int postion;

    private OnItemSelectedListener itemSelectedListener;


    public SelectableViewHolder(View view, OnItemSelectedListener listener) {
        super(view);
        itemSelectedListener = listener;
        checkedTextView = view.findViewById(R.id.checked_text_item);
        btnPlay =  view.findViewById(R.id.img_btn_play);
        ln_song =  view.findViewById(R.id.ln_song);

        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItem.isSelected() && getItemViewType() == MULTI_SELECTION) {
                    setChecked(false);
                } else {
                    setChecked(true);
                }
                itemSelectedListener.onItemSelected(mItem);
            }
        });


           btnPlay.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (!play && checked) {
                       btnPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                       play = true;
                       itemSelectedListener.onClick(mItem, play, postion);
                   } else {
                       btnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                       play = false;
                       itemSelectedListener.onClick(mItem, play, postion);
                   }

               }
           });


    }

    public void setChecked(boolean value) {
        if (value) {
            //ln_song.setBackgroundColor(Color.LTGRAY);
            checked = value;
        } else {
            //ln_song.setBackgroundColor(Color.TRANSPARENT);
            checked = value;
        }
        mItem.setSelected(value);
        checkedTextView.setChecked(value);

        btnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);


    }

    public interface OnItemSelectedListener {
        void onItemSelected(SelectableItem item);
        void onClick(SelectableItem item, boolean play, int position);
    }

}