package com.codepath.traintogether.adapters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.codepath.traintogether.R;
import com.codepath.traintogether.TrainTogetherApplication;
import com.codepath.traintogether.activities.LoginActivity;
import com.codepath.traintogether.models.Group;
import com.codepath.traintogether.models.User;
import com.codepath.traintogether.models.active.Place;
import com.codepath.traintogether.models.active.Result;
import com.codepath.traintogether.utils.Constants;
import com.codepath.traintogether.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ameyapandilwar on 8/17/16 at 2:18 PM.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvLocation;
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
            ivPoster = (ImageView) view.findViewById(R.id.ivPoster);
            ibJoin = (ImageButton) view.findViewById(R.id.ibJoin);

            ibJoin.setOnClickListener(v -> {
                Result event = mEvents.get(getLayoutPosition());

                mAuth = FirebaseAuth.getInstance();
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
                } else {
                    displayLoginDialog(getContext());
                }
            });
        }
    }

    private void displayLoginDialog(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Login");

        alertDialogBuilder
                .setMessage("You need to login!")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent intent = new Intent(context, LoginActivity.class);
                    getContext().startActivity(intent);
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.cancel();
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
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
                            "%s, %s · %s",
                            place.getCityName(),
                            place.getCountryName(),
                            Utils.getEventDate(event.getActivityStartDate())
                    )
            );
        } else {
            holder.tvLocation.setText("");
        }
        holder.tvTime.setText(Utils.getDaysToEvent(event.getActivityStartDate()));

        loadThumbnail(holder, place.getCityName());
    }

    private void loadThumbnail(ViewHolder holder, String cityName) {
        if (cityName.equalsIgnoreCase("boston")) {
            holder.ivPoster.setImageResource(R.drawable.boston);
        } else if (cityName.equalsIgnoreCase("san francisco")) {
            holder.ivPoster.setImageResource(R.drawable.san_francisco);
        } else if (cityName.equalsIgnoreCase("san diego")) {
            holder.ivPoster.setImageResource(R.drawable.san_diego);
        } else {
            holder.ivPoster.setImageResource(R.drawable.default_marathon);
        }
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

}