package com.codepath.traintogether.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.codepath.traintogether.R;
import com.codepath.traintogether.adapters.LeaderBoardAdapter;
import com.codepath.traintogether.models.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderBoardActivity extends AppCompatActivity {

    private static final String TAG = LeaderBoardActivity.class.getSimpleName();
    LeaderBoardAdapter leaderBoardAdapter;
    ArrayList<User> mUserList;
    GridLayoutManager gridLayoutManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.user_list)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mUserList = new ArrayList<>();
        for(int i=0; i < 10; i++) {
            mUserList.add(i, new User("Test" + String.valueOf(i), "Test Loc", i,i,i));
        }

        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        leaderBoardAdapter = new LeaderBoardAdapter(this, mUserList);
        recyclerView.setAdapter(leaderBoardAdapter);
        recyclerView.setHasFixedSize(true);
        leaderBoardAdapter.notifyDataSetChanged();
    }


}
