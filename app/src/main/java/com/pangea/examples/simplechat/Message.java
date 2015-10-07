package com.pangea.examples.simplechat;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.math.BigInteger;
import java.security.MessageDigest;

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

    public boolean getIsMine() {
        return ParseUser.getCurrentUser().getObjectId().equals(userId);
    }

    // Create a gravatar image based on the hash value obtained from userId
    public static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }
}
