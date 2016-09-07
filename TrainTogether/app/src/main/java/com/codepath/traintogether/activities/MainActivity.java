package com.codepath.traintogether.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.traintogether.R;
import com.codepath.traintogether.TrainTogetherApplication;
import com.codepath.traintogether.fragments.ChatFragment;
import com.codepath.traintogether.fragments.FeedFragment;
import com.codepath.traintogether.fragments.FilterSettingsDialogFragment;
import com.codepath.traintogether.models.FilterSettings;
import com.codepath.traintogether.models.User;
import com.codepath.traintogether.utils.Constants;
import com.codepath.traintogether.utils.StylishTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameyapandilwar on 8/17/16
 */
public class MainActivity extends BaseActivity implements FilterSettingsDialogFragment.FilterSettingsDialogListener {

    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;

    SharedPreferences preferences;

    FeedFragment fragment = new FeedFragment();

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabaseReference;

    ViewPager viewPager;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            checkCurrentUser(user.getUid());

        }



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new Adapter(getSupportFragmentManager());
        StylishTabLayout tabLayout = (StylishTabLayout) findViewById(R.id.tabs);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.flFeed);

        User currentUser = TrainTogetherApplication.getCurrentUser();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView, currentUser);
        }

        if (currentUser != null && currentUser.getGroups().size() > 0) {
            if (viewPager != null) {
                setupViewPager(viewPager);
            }
            tabLayout.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        } else {
            tabLayout.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);

            fragment.setArguments(getIntent().getExtras());

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.flFeed, fragment);
            fragmentTransaction.commit();
        }
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> showFilterSettingsDialog());

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragment.setQuery(query);
                refreshEvents();
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                fragment.setQuery("running");
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.invite_menu:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshEvents() {
        fragment.refreshEvents();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new FeedFragment(), "Feed");
        adapter.addFragment(new FeedFragment(), "Stats");
        adapter.addFragment(new ChatFragment(), "Chat");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView, User currentUser) {
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });

        // Inflate the header view at runtime
        if(currentUser != null) {
            View headerLayout =  navigationView.getHeaderView(0);
            // We can now look up items within the header if needed
            ImageView ivProfilePhoto = (ImageView) headerLayout.findViewById(R.id.ivProfilePhoto);
            ivProfilePhoto.setImageResource(0);
            Glide.with(getApplicationContext()).load(currentUser.getPhotoUrl()).into(ivProfilePhoto);
            Log.i(TAG, "user.getPhotoUrl(): " + currentUser.getPhotoUrl() + " name:" + currentUser.getDisplayName());
            TextView tvUserName = (TextView) headerLayout.findViewById(R.id.tvUserName);
            tvUserName.setText(currentUser.getDisplayName());
        }
    }

    private void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
//            case R.id.nav_chat:
//                startActivity(new Intent(MainActivity.this, ChatActivity.class));
//                break;
            case R.id.nav_login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.nav_leaderboard:
                startActivity(new Intent(MainActivity.this, LeaderBoardActivity.class));
                break;
            case R.id.nav_stats:
                startActivity(new Intent(MainActivity.this, StatsActivity.class));
                break;

            case R.id.nav_preference:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;

            case R.id.nav_requests:
                startActivity(new Intent(MainActivity.this, RequestActivity.class));
                break;

            case R.id.nav_schedule:
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
                break;

            default:
                break;
        }
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
    }

    private void showFilterSettingsDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterSettingsDialogFragment filterSettingsDialogFragment = FilterSettingsDialogFragment.newInstance("");
        filterSettingsDialogFragment.show(fm, "fragment_filter_settings");
    }

    @Override
    public void onUpdateFilterSettings(FilterSettings settings) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("lat_lon", settings.getLatLon());
        editor.putString("radius", settings.getRadius());
        editor.putString("city", settings.getCity());
        editor.putString("state", settings.getState());
        editor.putString("zip", settings.getZip());
        editor.putString("country", settings.getCountry());
        editor.putString("start_date", settings.getStartDate());

        editor.apply();

        refreshEvents();
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void checkCurrentUser(String uId) {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Query reference = mFirebaseDatabaseReference.child(Constants.USERS_CHILD).orderByChild("uid").equalTo(uId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        User u = snapshot.getValue(User.class);
                        TrainTogetherApplication.setCurrentUser(u);
                    } catch (Exception e) {
                        Log.d("DEBUG", e.getMessage());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}