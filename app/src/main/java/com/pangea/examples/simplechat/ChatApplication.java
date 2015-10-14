package com.pangea.examples.simplechat;

import android.app.Application;

import com.pangea.examples.simplechat.model.Message;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class ChatApplication extends Application {

    public static final String APPLICATION_ID = "1po4juGU0sU3i7JVm0u6P7y3EcbCS0pE3Mqj7Ad3";
    public static final String CLIENT_KEY = "GFsXIUvWtaOm5QXKlJs1BsRkLBJY8hJT7syHR1zU";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        // Register your parse models here
        ParseObject.registerSubclass(Message.class);
        // Existing initialization happens after all classes are registered
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
