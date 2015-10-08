package com.pangea.examples.simplechat.parse;

import com.parse.ParseUser;

public class UserUtils {

    public static String getMyUserId() {
        return ParseUser.getCurrentUser().getObjectId();
    }
}
