package com.nhattuan.growrichs.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.nhattuan.growrichs.Activity.DetailActivity;
import com.nhattuan.growrichs.R;
import com.nhattuan.growrichs.model.ObjGoals;

import java.util.List;

public class GoalAdapter extends RecyclerSwipeAdapter<GoalAdapter.GoalsHolder> {

    private List<ObjGoals> goalsList;
    private Context context;
    private GoalAdapterListener listener;
    public GoalAdapter(List<ObjGoals> goalsList, Context context, GoalAdapterListener listener) {
        this.goalsList = goalsList;
        this.context = context;
        this.listener = listener;
    }
    public void ChangeList(List<ObjGoals> goalsList)
    {
        this.goalsList=goalsList;
        notifyDataSetChanged();
    }

    @Override
    public GoalsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goal, parent, false);
        return new GoalsHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoalsHolder viewHolder, final int position) {

        final ObjGoals goals = goalsList.get(position);

        viewHolder.tv_stt.setText("#"+(position+1)+": ");
        viewHolder.goal_item_tv.setText(goals.getmTilte());

        viewHolder.goal_item_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send selected contact in callback
                listener.onClick(goalsList.get(position));
            }
        });
        viewHolder.goal_item_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(goalsList.get(position), v);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class GoalsHolder extends RecyclerView.ViewHolder {

        public TextView goal_item_tv,tv_stt;//,Delete;

        GoalsHolder(View itemView) {
            super(itemView);
            goal_item_tv = itemView.findViewById(R.id.goal_item_tv);
            //swipeLayout =  itemView.findViewById(R.id.swipe);
            //Delete =  itemView.findViewById(R.id.Delete);
            tv_stt =  itemView.findViewById(R.id.tv_stt);
            //linear_goal =  itemView.findViewById(R.id.linear_goal);
        }
    }

    public interface GoalAdapterListener {
         void onClick(ObjGoals goals);
         boolean onLongClick(ObjGoals goals, View v);
         void onDelete(ObjGoals goals);
    }

    public static int convertDpToPixel(int dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

}
