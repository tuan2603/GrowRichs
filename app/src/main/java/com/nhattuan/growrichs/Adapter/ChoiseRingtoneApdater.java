package com.nhattuan.growrichs.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.model.SelectableItem;
import com.nhattuan.growrichs.model.Song;

import java.util.ArrayList;
import java.util.List;

public class ChoiseRingtoneApdater extends RecyclerView.Adapter
        implements SelectableViewHolder.OnItemSelectedListener
{

    private List<Song> songList;
    private List<SelectableItem> mValues;
    private Context context;
    private boolean isMultiSelectionEnabled = false;
    SelectableViewHolder.OnItemSelectedListener listener;

    public ChoiseRingtoneApdater(List<Song> songList, Context context
            ,  SelectableViewHolder.OnItemSelectedListener listener
            , boolean isMultiSelectionEnabled )
    {
        this.songList = songList;
        this.context = context;
        this.listener = listener;

        this.isMultiSelectionEnabled = isMultiSelectionEnabled;

        mValues = new ArrayList<>();
        for (Song item : songList) {
            mValues.add(new SelectableItem(item, false));
        }
    }

    public void Changlist( List<Song> songList){
        this.songList = songList;
        notifyDataSetChanged();
    }

    @Override
    public SelectableViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song, parent, false);
        return new SelectableViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        SelectableViewHolder holder = (SelectableViewHolder) viewHolder;
        SelectableItem selectableItem = mValues.get(position);
        String name = selectableItem.getmTitle();
        holder.checkedTextView.setText("  "+ name);
        if (isMultiSelectionEnabled) {
            TypedValue value = new TypedValue();
            holder.checkedTextView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.checkedTextView.setCheckMarkDrawable(checkMarkDrawableResId);
        } else {
            //            TypedValue value = new TypedValue();
            //            holder.checkedTextView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, false);
            //            int checkMarkDrawableResId = value.resourceId;
            //            holder.checkedTextView.setCheckMarkDrawable(checkMarkDrawableResId);checkMarkDrawableResId
        }

        holder.mItem = selectableItem;
        holder.postion = position;

        holder.setChecked(holder.mItem.isSelected());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectionEnabled){
            return SelectableViewHolder.MULTI_SELECTION;
        }
        else{
            return SelectableViewHolder.SINGLE_SELECTION;
        }
    }


    @Override
    public void onItemSelected(SelectableItem item) {
        if (!isMultiSelectionEnabled) {
            for (SelectableItem selectableItem : mValues) {
                if (!selectableItem.equals(item)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(item)
                        && item.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onItemSelected(item);
    }

    @Override
    public void onClick(SelectableItem item, boolean play, int position) {
        listener.onClick(item,play,position);
    }


}
