package com.codepath.traintogether.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.traintogether.R;
import com.codepath.traintogether.activities.FacebookLoginActivity;
import com.codepath.traintogether.models.Group;
import com.codepath.traintogether.models.User;
import com.codepath.traintogether.models.active.AssetDescription;
import com.codepath.traintogether.models.active.Result;
import com.codepath.traintogether.utils.Constants;
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
        public TextView tvDescription;
        public ImageView ivPoster;
        public ImageButton ibJoin;

        private FirebaseUser loggedUser;
        private FirebaseAuth mAuth;
        private DatabaseReference mFirebaseDatabaseReference;

        public ViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            ivPoster = (ImageView) view.findViewById(R.id.ivPoster);
            ibJoin = (ImageButton) view.findViewById(R.id.ibJoin);

            ibJoin.setOnClickListener(v -> {
                Result event = mEvents.get(getLayoutPosition());
                Toast.makeText(mContext, event.getAssetName(), Toast.LENGTH_SHORT).show();


                FacebookLoginActivity fbActivity = new FacebookLoginActivity();

                mAuth = FirebaseAuth.getInstance();
                //loggedUser = fbActivity.getUser();
                loggedUser = mAuth.getCurrentUser();
                if(loggedUser != null) {
                    Group group = new Group();
                    User user = new User();
                    user.emailId = loggedUser.getEmail();
                    user.displayName = loggedUser.getDisplayName();
                    user.uid = loggedUser.getUid();
                    group.eventId = event.getAssetGuid();
                    group.users.add(user);
                    mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                    mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).push().setValue(group);                }
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
        List<AssetDescription> descriptions = event.getAssetDescriptions();
        if (descriptions.size() > 0) {
            holder.tvDescription.setText(Html.fromHtml(descriptions.get(0).getDescription()));
        } else {
            holder.tvDescription.setText(Html.fromHtml(""));
        }
        Glide.with(getContext()).load(event.getLogoUrlAdr()).into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

}