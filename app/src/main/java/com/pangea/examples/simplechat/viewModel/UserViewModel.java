package com.pangea.examples.simplechat.viewModel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.pangea.examples.simplechat.gravatar.Gravatar;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

public class UserViewModel extends BaseObservable {

    private String userId;
    private String userName;

    public UserViewModel(ParseUser user) {
        this.userId = user.getObjectId();
        this.userName = user.getUsername().substring(0, 5);
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String userId) {
        Picasso.with(view.getContext()).load(Gravatar.getProfileUrl(userId)).into(view);
    }
}
