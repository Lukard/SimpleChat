package com.pangea.examples.simplechat.parse;

import com.pangea.examples.simplechat.model.Message;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {

    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;

    // Query messages from Parse so we can load them into the chat adapter
    public static void receiveMessage(FindCallback<Message> callback, String receiverId) {
        // Construct query to execute
        ParseQuery<Message> query1 = ParseQuery.getQuery(Message.class);
        query1.whereEqualTo(Message.USER_ID_ARG, UserUtils.getMyUserId());
        query1.whereEqualTo(Message.RECEIVER_ID_ARG, receiverId);
        ParseQuery<Message> query2 = ParseQuery.getQuery(Message.class);
        query2.whereEqualTo(Message.RECEIVER_ID_ARG, UserUtils.getMyUserId());
        query2.whereEqualTo(Message.USER_ID_ARG, receiverId);
        List<ParseQuery<Message>> queries = new ArrayList<>(2);
        queries.add(query1);
        queries.add(query2);
        ParseQuery<Message> query = ParseQuery.or(queries);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByDescending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(callback);
    }
}
