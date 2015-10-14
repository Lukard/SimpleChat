package com.pangea.examples.simplechat.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.pangea.examples.simplechat.R;
import com.pangea.examples.simplechat.databinding.ActivityUsersBinding;
import com.pangea.examples.simplechat.model.Message;
import com.pangea.examples.simplechat.viewModel.UsersViewModel;
import com.pangea.examples.simplechat.views.UsersAdapter;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UsersActivity extends AppCompatActivity implements UsersAdapter.OnUserClickListener {

    private static final String TAG = UsersActivity.class.getName();

    ActivityUsersBinding binding;
    UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Message tempMessage = Message.fromBundle(bundle);
            launchChatActivity(tempMessage.getReceiverId());
        }

        // User login
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            setUpRecyclerView();
        } else { // If not logged in, login as a new anonymous user
            login();
        }
    }

    private void setUpRecyclerView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users);
        UsersViewModel viewModel = new UsersViewModel();
        binding.setUsers(viewModel);

        binding.rvUsers.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        binding.rvUsers.setLayoutManager(layoutManager);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        try {
            List<ParseUser> users = query.find();
            users.remove(ParseUser.getCurrentUser());
            adapter = new UsersAdapter(users, this);
            binding.rvUsers.setAdapter(adapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserClick(ParseUser user) {
        String userId = user.getObjectId();

        launchChatActivity(userId);
    }

    private void launchChatActivity(String receiverId) {
        Intent activity = new Intent(this, ChatActivity.class);
        activity.putExtra(ChatActivity.RECEIVER_ID_ARG, receiverId);
        startActivity(activity);
    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed: " + e.toString());
                } else {
                    ParseInstallation.getCurrentInstallation().put("user", user.getObjectId());
                    ParseInstallation.getCurrentInstallation().saveInBackground();
                    setUpRecyclerView();
                }
            }
        });
    }
}
