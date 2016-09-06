package com.codepath.traintogether.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.traintogether.R;
import com.codepath.traintogether.TrainTogetherApplication;
import com.codepath.traintogether.models.Group;
import com.codepath.traintogether.models.User;
import com.codepath.traintogether.models.active.Place;
import com.codepath.traintogether.models.active.Result;
import com.codepath.traintogether.utils.Constants;
import com.codepath.traintogether.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by ameyapandilwar on 8/17/16 at 2:18 PM.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvLocation;
        public TextView tvCalendar;
        public TextView tvTime;

        public ImageView ivPoster;
        public ImageButton ibJoin;

        private FirebaseUser loggedUser;
        private FirebaseAuth mAuth;
        private DatabaseReference mFirebaseDatabaseReference;

        public ViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvLocation = (TextView) view.findViewById(R.id.tvLocation);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            tvCalendar = (TextView) view.findViewById(R.id.tvCalendar);

            ivPoster = (ImageView) view.findViewById(R.id.ivPoster);
            ibJoin = (ImageButton) view.findViewById(R.id.ibJoin);

            ibJoin.setOnClickListener(v -> {
                Result event = mEvents.get(getLayoutPosition());

                mAuth = FirebaseAuth.getInstance();
                //loggedUser = fbActivity.getUser();
                loggedUser = mAuth.getCurrentUser();
                if (loggedUser != null) {
                    Group group = new Group();
                    User user = new User();
                    user.emailId = loggedUser.getEmail();
                    user.displayName = loggedUser.getDisplayName();
                    user.uid = loggedUser.getUid();
                    group.eventId = event.getAssetGuid();
                    mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                    String key = mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).push().getKey();
                    group.key = key;

                    user.groups.add(group.key);
                    TrainTogetherApplication.setCurrentUser(user);
                    mFirebaseDatabaseReference.child(Constants.USERS_CHILD).child(user.getUid()).setValue(user);

                    group.users.add(user);
                    mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).child(key).setValue(group);
                }
            });
        }
    }

    private List<Result> mEvents;
    private Context mContext;

    public EventsAdapter(Context context, List<Result> events) {
        mEvents = events;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        return new ViewHolder(inflater.inflate(R.layout.item_event, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result event = mEvents.get(position);

        holder.tvTitle.setText(event.getAssetName());
        holder.ivPoster.setImageResource(0);
        Place place = event.getPlace();
        if (place != null) {
            holder.tvLocation.setText(
                    String.format(
                            "%s, %s",
                            place.getCityName(),
                            place.getCountryName()
                    )
            );
        } else {
            holder.tvLocation.setText("");
        }


        holder.tvCalendar.setText(Utils.getEventDate(event.getActivityStartDate()));
        holder.tvTime.setText(Utils.getDaysToEvent(event.getActivityStartDate()));

        loadThumbnail(holder, place.getCityName());
    }

    private void loadThumbnail(ViewHolder holder, String cityName) {
        int[] randBg = {R.drawable.mara1, R.drawable.mara2, R.drawable.mara3};
        if (cityName.equalsIgnoreCase("boston")) {
            Glide.with(getContext()).load(R.drawable.boston1).into(holder.ivPoster);
        } else if (cityName.equalsIgnoreCase("san francisco")) {
            Glide.with(getContext()).load(R.drawable.san_francisco).into(holder.ivPoster);
        } else if (cityName.equalsIgnoreCase("san diego")) {
            Glide.with(getContext()).load(R.drawable.san_diego).into(holder.ivPoster);
        } else {

            Glide.with(getContext()).load(randBg[Utils.randomWithRange(0,2)]).into(holder.ivPoster);
        }
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

}