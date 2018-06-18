package com.nhattuan.growrichs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.nhattuan.growrichs.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fifu on 05-Feb-18.
 */

public class SnoozeAdapter extends BaseAdapter {
    private Context context;
    private List<String> snoozeList;
    ItemClick itemClick;
    public int chekc=-1;
    public SnoozeAdapter(Context context,ItemClick itemClick)
    {
        this.context=context;
        this.snoozeList=new ArrayList<>();
        snoozeList.add("Off");
        snoozeList.add("1 minutes");
        snoozeList.add("3 minutes");
        snoozeList.add("5 minutes");
        snoozeList.add("10 minutes");
        snoozeList.add("15 minutes");
        this.itemClick=itemClick;
    }
    @Override
    public int getCount() {
        return snoozeList.size();
    }

    @Override
    public Object getItem(int i) {
        return snoozeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override

    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder viewHolder;
        if(view == null)
        {
            view = mInflater.inflate(R.layout.snooze_item, null);
            viewHolder = new ViewHolder();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.Click(i,view);
                }
            });
            viewHolder.item= view.findViewById(R.id.item);
            viewHolder.check=view.findViewById(R.id.check);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder. item.setText(snoozeList.get(i));
        if(i==chekc) viewHolder.check.setChecked(true);
        else viewHolder.check.setChecked(false);
        final View tmp=view;
        viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    itemClick.Click(i,tmp);
                }
            }
        });
        return  view;
    }
    class ViewHolder {
        CheckedTextView item;
        RadioButton check;
        public ViewHolder() {

        }
    }
    public interface ItemClick
    {
        void Click(int positon, View view);
    }
}

