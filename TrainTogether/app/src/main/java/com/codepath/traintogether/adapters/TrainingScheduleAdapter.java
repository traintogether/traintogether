package com.codepath.traintogether.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.traintogether.R;
import com.codepath.traintogether.models.Days;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alishaalam on 8/28/16.
 */
public class TrainingScheduleAdapter extends RecyclerView.Adapter<TrainingScheduleAdapter.ScheduleViewHolder> {

    private static final String TAG = TrainingScheduleAdapter.class.getSimpleName();
    Context mContext;
    private List<Days> mDaysList = new ArrayList<>();

    public TrainingScheduleAdapter(Context mContext, List<Days> mDaysList) {
        this.mContext = mContext;
        this.mDaysList = mDaysList;
    }


    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.content_schedule_item, parent, false);
        ScheduleViewHolder scheduleViewHolder = new ScheduleViewHolder(view);
        return scheduleViewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        final Days day = mDaysList.get(position);
        if (day != null) {
            holder.vDay.setText(day.getDay());
            holder.vDate.setText(day.getDate());
            holder.vStatus.setText(day.getStatus());
            holder.vStatus_min_mile.setText(day.getTime_mile_status());
            holder.vWorkout.setText(day.getWorkout());
        }
    }

    @Override
    public int getItemCount() {
        return mDaysList.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day)
        public TextView vDay;

        @BindView(R.id.date)
        public TextView vDate;

        @Nullable
        @BindView(R.id.ivStatus)
        public ImageView vImgStatus;

        @BindView(R.id.tvStatus)
        public TextView vStatus;

        @BindView(R.id.tv_status_time_mile)
        public TextView vStatus_min_mile;

        @BindView(R.id.tvWorkout)
        public TextView vWorkout;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
