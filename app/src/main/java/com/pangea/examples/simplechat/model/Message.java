package com.pangea.examples.simplechat.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {

    public String userId;
    private String body;

    public String getUserId() {
        return getString("userId");
    }

    public String getBody() {
        return getString("body");
    }

    public void setUserId(String userId) {
        this.userId = userId;
        put("userId", userId);
    }

    public void setBody(String body) {
        this.body = body;
        put("body", body);
    }
}
