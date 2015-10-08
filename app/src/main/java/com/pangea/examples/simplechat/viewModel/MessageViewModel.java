package com.pangea.examples.simplechat.viewModel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.CardView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.pangea.examples.simplechat.model.Message;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

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

    public String getUserId() {
        return message.getUserId();
    }

    public boolean getIsMine() {
        return ParseUser.getCurrentUser().getObjectId().equals(message.getUserId());
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String userId) {
        Picasso.with(view.getContext()).load(getProfileUrl(userId)).into(view);
    }

    @BindingAdapter({"android:layout_gravity"})
    public static void setLayoutGravity(CardView view, int gravityMode) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = gravityMode;
        view.setLayoutParams(params);
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
