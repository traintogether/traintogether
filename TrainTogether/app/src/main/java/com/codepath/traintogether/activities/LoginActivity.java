package com.codepath.traintogether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.traintogether.R;
import com.codepath.traintogether.TrainTogetherApplication;
import com.codepath.traintogether.models.User;
import com.codepath.traintogether.utils.Constants;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ameyapandilwar on 8/17/16
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.button_facebook_login)
    LoginButton btnFacebookLogin;

    @BindView(R.id.button_facebook_signout)
    Button btnFacebookSignout;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.login_text)
    TextView tvLoginText;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    public FirebaseUser getUser() {
        return user;
    }

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mFirebaseDatabaseReference;

    private CallbackManager mCallbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnFacebookSignout.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
            updateUI(user);
        };

        tvLoginText.setOnClickListener(view -> {
            if (tvLoginText.getText().toString().equalsIgnoreCase("I prefer email")) {
                tvLoginText.setText("Nah, I'll use Facebook");

                btnFacebookLogin.setVisibility(View.INVISIBLE);
                etEmail.setVisibility(View.VISIBLE);
                etPassword.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.VISIBLE);
            } else {
                tvLoginText.setText("I prefer email");

                btnFacebookLogin.setVisibility(View.VISIBLE);
                etEmail.setVisibility(View.GONE);
                etPassword.setVisibility(View.GONE);
                btnLogin.setVisibility(View.GONE);
            }
        });

        initializeFacebookLogin();

//  TODO: create more users
//  for(int i=19; i<40; i++) {
//            mAuth.createUserWithEmailAndPassword("tt"+ i  + "@gmail.com", "123456")
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//
//                            // If sign in fails, display a message to the user. If sign in succeeds
//                            // the auth state listener will be notified and logic to handle the
//                            // signed in user can be handled in the listener.
//                            if (!task.isSuccessful()) {
//                                Toast.makeText(FacebookLoginActivity.this, R.string.auth_failed,
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//        }
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
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
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

            btnFacebookLogin.setVisibility(View.GONE);
            btnFacebookSignout.setVisibility(View.VISIBLE);

            tvLoginText.setVisibility(View.GONE);

            mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

            addNewUser(user);

        } else {

            btnFacebookLogin.setVisibility(View.VISIBLE);
            btnFacebookSignout.setVisibility(View.GONE);

            tvLoginText.setVisibility(View.VISIBLE);

        }
    }

    private void addNewUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.emailId = firebaseUser.getEmail();
        user.displayName = firebaseUser.getDisplayName();
        user.uid = firebaseUser.getUid();

        FirebaseMessaging.getInstance().subscribeToTopic(String.format("user_%s", user.uid));

        Query reference = mFirebaseDatabaseReference.child(Constants.USERS_CHILD).orderByChild("uid").equalTo(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean exists = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        User u = snapshot.getValue(User.class);
                        TrainTogetherApplication.setCurrentUser(u);
                        exists = true;
                    }catch (Exception e){
                        Log.i(TAG, e.getMessage());
                    }
                }

                if (!exists) {
                    if (user != null) {
                        mFirebaseDatabaseReference.child(Constants.USERS_CHILD).child(user.getUid()).setValue(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_facebook_signout) {
            signOut();
        }
    }

    public void signInUser(View view) {
        showProgressDialog();

        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                        Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        hideProgressDialog();
                        finish();
                    }

                });

    }
}
