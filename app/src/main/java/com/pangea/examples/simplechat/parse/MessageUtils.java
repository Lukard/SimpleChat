package com.pangea.examples.simplechat.parse;

import com.pangea.examples.simplechat.model.Message;
import com.parse.FindCallback;
import com.parse.ParseQuery;

public class MessageUtils {

    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;

    // Query messages from Parse so we can load them into the chat adapter
    public static void receiveMessage(FindCallback<Message> callback, String receiverId) {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Just get the required objects
        query.whereEqualTo(Message.USER_ID_ARG, UserUtils.getMyUserId());
        query.whereEqualTo(Message.RECEIVER_ID_ARG, receiverId);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByDescending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(callback);
    }
}
