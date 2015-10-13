package com.pangea.examples.simplechat.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.pangea.examples.simplechat.R;
import com.pangea.examples.simplechat.databinding.ActivityChatBinding;
import com.pangea.examples.simplechat.model.Message;
import com.pangea.examples.simplechat.parse.MessageUtils;
import com.pangea.examples.simplechat.viewModel.ChatViewModel;
import com.pangea.examples.simplechat.views.MessagesAdapter;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements FindCallback<Message>, SaveCallback {

    private static final String TAG = ChatActivity.class.getName();
    public static final String RECEIVER_ID_ARG = "receiverId";

    private String receiverId;

    ActivityChatBinding binding;

    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        receiverId = getIntent().getStringExtra(RECEIVER_ID_ARG);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        ChatViewModel viewModel = new ChatViewModel(receiverId, this);
        binding.setChat(viewModel);

        // User login
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            setupMessagePosting();
        } else { // If not logged in, login as a new anonymous user
            login();
        }

    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed: " + e.toString());
                } else {
                    setupMessagePosting();
                }
            }
        });
    }

    // Setup button event handler which posts the entered message to Parse
    private void setupMessagePosting() {
        binding.rvChat.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        binding.rvChat.setLayoutManager(mLayoutManager);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        binding.rvChat.setAdapter(mAdapter);

        receiveMessage();
    }

    // Query messages from Parse so we can load them into the chat adapter
    private void receiveMessage() {
        MessageUtils.receiveMessage(this, receiverId);
    }

    @Override
    public void done(List<Message> messages, ParseException e) {
        if (e == null) {
            messages.removeAll(mMessages);
            if (messages.size() > 0) {
                mMessages.addAll(0, messages);
                mAdapter.notifyDataSetChanged(); // update adapter
            }
        } else {
            Log.d("message", "Error: " + e.getMessage());
        }
    }

    @Override
    public void done(ParseException e) {
        receiveMessage();
    }
}
