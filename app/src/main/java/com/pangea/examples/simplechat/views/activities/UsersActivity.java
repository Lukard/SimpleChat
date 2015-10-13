package com.pangea.examples.simplechat.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.pangea.examples.simplechat.R;
import com.pangea.examples.simplechat.databinding.ActivityUsersBinding;
import com.pangea.examples.simplechat.viewModel.UsersViewModel;
import com.pangea.examples.simplechat.views.UsersAdapter;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UsersActivity extends AppCompatActivity implements UsersAdapter.OnUserClickListener {

    ActivityUsersBinding binding;
    UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_users);
        UsersViewModel viewModel = new UsersViewModel();
        binding.setUsers(viewModel);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
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

        Intent activity = new Intent(this, ChatActivity.class);
        activity.putExtra(ChatActivity.RECEIVER_ID_ARG, userId);
        startActivity(activity);
    }
}
