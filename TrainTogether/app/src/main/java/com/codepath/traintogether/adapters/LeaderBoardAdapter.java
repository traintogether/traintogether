package com.codepath.traintogether.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.codepath.traintogether.R;
import com.codepath.traintogether.models.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alishaalam on 8/18/16.
 */
public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>{

    Context mContext;
    ArrayList<User> mUserList = new ArrayList<User>();
    User mUser;

    public LeaderBoardAdapter(Context context, ArrayList<User> userArrayList) {
        this.mContext = context;
        this.mUserList = userArrayList;
    }

    @Override
    public LeaderBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view =inflater.inflate(R.layout.activity_leaderboard_item, parent, false);
        LeaderBoardViewHolder leaderBoardViewHolder = new LeaderBoardViewHolder(view);
        return  leaderBoardViewHolder;
    }

    @Override
    public void onBindViewHolder(LeaderBoardViewHolder holder, int position) {
        mUser = mUserList.get(position);

        if(mUser != null) {
            Glide.with(mContext).load(mUser.getProfileImageUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.vUserProfilePic) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.vUserProfilePic.setImageDrawable(circularBitmapDrawable);
                }
            });

            holder.vUserLocation.setText(mUser.getLocation());
            holder.vUserRank.setText(String.valueOf(mUser.getRank()));
            holder.vUserMiles.setText(String.valueOf(mUser.getMiles()) + " miles");
            holder.vUserRuns.setText(String.valueOf(mUser.getRuns()) + " runs");
        }
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


    public static class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_profile_pic)
        ImageView vUserProfilePic;

        @BindView(R.id.user_location)
        TextView vUserLocation;

        @BindView(R.id.user_rank)
        TextView vUserRank;

        @BindView(R.id.user_miles)
        TextView vUserMiles;

        @BindView(R.id.user_runs)
        TextView vUserRuns;

        public LeaderBoardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
