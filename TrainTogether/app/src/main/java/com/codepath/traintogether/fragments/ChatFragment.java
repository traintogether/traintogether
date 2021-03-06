package com.codepath.traintogether.fragments;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import com.bumptech.glide.Glide;
import com.codepath.traintogether.R;
import com.codepath.traintogether.TrainTogetherApplication;
import com.codepath.traintogether.activities.LoginActivity;
import com.codepath.traintogether.models.ChatMessage;
import com.codepath.traintogether.models.User;
import com.codepath.traintogether.utils.Constants;
import com.codepath.traintogether.utils.ItemSpaceDecoration;
import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ameyapandilwar on 8/17/16
 */
public class ChatFragment extends BaseFragment {

    private static final String TAG = "ChatActivity";

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
        }
    }

    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;

    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.rvMessages)
    RecyclerView rvMessages;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;
    @BindView(R.id.etMessage)
    EditText etMessage;

    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder> mFirebaseAdapter;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private String chatRoom = Constants.MESSAGES_CHILD;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, v);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mUsername = Constants.ANONYMOUS;

        initializeChatRoom();

        initializeFirebase();

        setupDisplayMessages();

        defineFirebaseRemoteConfigSettings();

        fetchRemoteConfig();

        applyFilters();

        btnSend.setOnClickListener(view -> {
            ChatMessage message = new ChatMessage(etMessage.getText().toString(), mUsername, mPhotoUrl);
            mFirebaseDatabaseReference.child(chatRoom).push().setValue(message);
            etMessage.setText("");
        });

        pbLoading.setVisibility(ProgressBar.INVISIBLE);

        return v;
    }

    private void initializeChatRoom() {
        User currentUser = TrainTogetherApplication.getCurrentUser();
        if (currentUser.getGroups().size() > 0) {
            chatRoom = String.format("%s%s", Constants.MESSAGES_CHILD, currentUser.getGroups().get(0));
        }
    }

    private void applyFilters() {
        etMessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSharedPreferences
                .getInt(Constants.FRIENDLY_MSG_LENGTH, Constants.DEFAULT_MSG_LENGTH_LIMIT))});
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    btnSend.setEnabled(true);
                } else {
                    btnSend.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void setupDisplayMessages() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder>(
                ChatMessage.class,
                R.layout.item_message,
                MessageViewHolder.class,
                mFirebaseDatabaseReference.child(chatRoom)) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, ChatMessage chatMessage, int position) {
                pbLoading.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.messageTextView.setText(chatMessage.getText());
                viewHolder.messengerTextView.setText(chatMessage.getName());
                if (chatMessage.getPhotoUrl() == null) {
                    viewHolder.messengerImageView.setImageDrawable(ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_account_circle_black_36dp));
                } else {
                    Glide.with(getContext())
                            .load(chatMessage.getPhotoUrl())
                            .into(viewHolder.messengerImageView);
                }
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    rvMessages.scrollToPosition(positionStart);
                }
            }
        });

        rvMessages.setLayoutManager(mLinearLayoutManager);
        rvMessages.setAdapter(mFirebaseAdapter);
        rvMessages.addItemDecoration(new ItemSpaceDecoration(5));
    }

    private void defineFirebaseRemoteConfigSettings() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put(Constants.FRIENDLY_MSG_LENGTH, 10L);

        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);
    }

    private void initializeFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            Uri photoUrl = mFirebaseUser.getPhotoUrl();
            mPhotoUrl = photoUrl != null ? photoUrl.toString() : "";
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.invite_menu:
                sendInvitation();
                return true;
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                LoginManager.getInstance().logOut();
                mFirebaseUser = null;
                mUsername = Constants.ANONYMOUS;
                mPhotoUrl = null;
                startActivity(new Intent(getContext(), LoginActivity.class));
                return true;
            case R.id.fresh_config_menu:
                fetchRemoteConfig();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, Constants.REQUEST_INVITE);
    }

    public void fetchRemoteConfig() {
        long cacheExpiration = Constants.ONE_HOUR;
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(aVoid -> {
                    mFirebaseRemoteConfig.activateFetched();
                    applyRetrievedLengthLimit();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error fetching config: " + e.getMessage());
                    applyRetrievedLengthLimit();
                });
    }

    private void applyRetrievedLengthLimit() {
        Long friendly_msg_length = mFirebaseRemoteConfig.getLong(Constants.FRIENDLY_MSG_LENGTH);
        etMessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(friendly_msg_length.intValue())});
        Log.d(TAG, "FML is: " + friendly_msg_length);
    }

}