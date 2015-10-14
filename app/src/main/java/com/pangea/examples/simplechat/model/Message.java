package com.pangea.examples.simplechat.model;

import android.os.Bundle;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;

@ParseClassName("Message")
public class Message extends ParseObject {

    public static final String USER_ID_ARG = "userId";
    public static final String RECEIVER_ID_ARG = "receiverId";
    public static final String BODY_ARG = "body";

    private String userId;
    private String receiverId;
    private String body;

    public Message() {
    }

    private Message(String userId, String receiverId, String body) {
        setUserId(userId);
        setReceiverId(receiverId);
        setBody(body);
    }

    public String getUserId() {
        return getString(USER_ID_ARG);
    }

    public String getReceiverId() {
        return getString(RECEIVER_ID_ARG);
    }

    public String getBody() {
        return getString(BODY_ARG);
    }

    public void setUserId(String userId) {
        this.userId = userId;
        put(USER_ID_ARG, userId);
    }

    public void setReceiverId(String receiverid) {
        this.receiverId = receiverid;
        put(RECEIVER_ID_ARG, receiverId);
    }

    public void setBody(String body) {
        this.body = body;
        put(BODY_ARG, body);
    }

    public static Message fromBundle(Bundle args) {
        try {
            JSONObject data = new JSONObject(args.getString("com.parse.Data"));
            String receiverId = data.getString(RECEIVER_ID_ARG);
            String body = data.getString(BODY_ARG);
            return new Message("", receiverId, body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        for (String key : args.keySet()) {
//            Object value = args.get(key);
//            Log.d("TAG", String.format("%s %s (%s)", key,
//                    value.toString(), value.getClass().getName()));
//        }
        return null;
    }
}
