package com.pangea.examples.simplechat.viewModel;

import android.databinding.BaseObservable;

import com.pangea.examples.simplechat.model.Message;
import com.parse.ParseUser;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MessageViewModel extends BaseObservable {

    private Message message;

    public MessageViewModel(Message message) {
        this.message = message;
    }

    public String getMessageText() {
        return message.getBody();
    }

    public boolean getIsMine() {
        return ParseUser.getCurrentUser().getObjectId().equals(message.getUserId());
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
