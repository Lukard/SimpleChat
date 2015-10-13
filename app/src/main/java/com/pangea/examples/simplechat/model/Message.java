package com.pangea.examples.simplechat.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {

    public static final String USER_ID_ARG = "userId";
    public static final String RECEIVER_ID_ARG = "receiverId";
    public static final String BODY_ARG = "body";

    private String userId;
    private String receiverId;
    private String body;

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

}
