package com.pangea.examples.simplechat;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pangea.examples.simplechat.model.Message;
import com.pangea.examples.simplechat.parse.MessageUtils;
import com.pangea.examples.simplechat.views.MessagesAdapter;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_chat)
public class ChatActivity extends AppCompatActivity {

    private static final String TAG = ChatActivity.class.getName();

    private static String sUserId;

    // Create a handler which can run code periodically
    private Handler handler = new Handler();

    @ViewById
    EditText etMessage;
    @ViewById
    Button btSend;
    @ViewById
    RecyclerView rvChat;

    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;

    @AfterViews
    void init() {
        // User login
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            startWithCurrentUser();
        } else { // If not logged in, login as a new anonymous user
            login();
        }

        // Run the runnable object defined every 100ms
        handler.postDelayed(runnable, 100);
    }

    // Defines a runnable which is run every 100ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };

    private void refreshMessages() {
        receiveMessage();
    }

    // Get the userId from the cached currentUser object
    private void startWithCurrentUser() {
        sUserId = ParseUser.getCurrentUser().getObjectId();
        setupMessagePosting();
    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed: " + e.toString());
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }

    // Setup button event handler which posts the entered message to Parse
    private void setupMessagePosting() {
        rvChat.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setReverseLayout(true);
        rvChat.setLayoutManager(mLayoutManager);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        rvChat.setAdapter(mAdapter);
        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String body = etMessage.getText().toString();
                // Use Message model to create new messages now
                Message message = new Message();
                message.setUserId(sUserId);
                message.setBody(body);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        receiveMessage();
                    }
                });
                etMessage.setText("");
            }
        });
    }

    // Query messages from Parse so we can load them into the chat adapter
    private void receiveMessage() {
        MessageUtils.receiveMessage(messageCallback);
    }

    FindCallback<Message> messageCallback = new FindCallback<Message>() {
        public void done(List<Message> messages, ParseException e) {
            if (e == null) {
                messages.removeAll(mMessages);
                if (messages.size() > 0) {
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    rvChat.scrollToPosition(mAdapter.getItemCount() - 1);
                }
            } else {
                Log.d("message", "Error: " + e.getMessage());
            }
        }
    };
}
