package com.codepath.traintogether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.traintogether.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ameyapandilwar on 8/17/16
 */
public class FacebookLoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "FacebookLoginActivity";

    @BindView(R.id.status)
    TextView mStatusTextView;
    @BindView(R.id.detail)
    TextView mDetailTextView;
    @BindView(R.id.button_facebook_login)
    LoginButton btnFacebookLogin;
    @BindView(R.id.button_facebook_signout)
    Button btnFacebookSignout;
    @BindView(R.id.button_chat)
    Button btnChat;
    @BindView(R.id.button_feed)
    Button btnFeed;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private CallbackManager mCallbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook_login);
        ButterKnife.bind(this);

        btnFacebookSignout.setOnClickListener(this);
        btnChat.setOnClickListener(this);
        btnFeed.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
            updateUI(user);
        };

        initializeFacebookLogin();
    }

    private void initializeFacebookLogin() {
        mCallbackManager = CallbackManager.Factory.create();
        btnFacebookLogin.setReadPermissions("email", "public_profile");
        btnFacebookLogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                updateUI(null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                updateUI(null);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        showProgressDialog();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(FacebookLoginActivity.this, ChatActivity.class));
                        hideProgressDialog();
                        finish();
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.facebook_status_fmt, user.getDisplayName()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            btnFacebookLogin.setVisibility(View.GONE);
            btnFacebookSignout.setVisibility(View.VISIBLE);
            btnChat.setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            btnFacebookLogin.setVisibility(View.VISIBLE);
            btnFacebookSignout.setVisibility(View.GONE);
            btnChat.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_facebook_signout) {
            signOut();
        } else if (i == R.id.button_chat) {
            navigateToChat();
        } else if (i == R.id.button_feed) {
            navigateToFeed();
        }
    }

    private void navigateToChat() {
        startActivity(new Intent(this, ChatActivity.class));
    }

    private void navigateToFeed() {
        startActivity(new Intent(this, FeedActivity.class));
    }
}