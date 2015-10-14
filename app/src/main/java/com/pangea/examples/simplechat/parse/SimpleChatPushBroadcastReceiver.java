package com.pangea.examples.simplechat.parse;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.pangea.examples.simplechat.views.activities.ChatActivity;
import com.parse.ParsePushBroadcastReceiver;

public class SimpleChatPushBroadcastReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected Notification getNotification(Context context, Intent intent) {
        Intent notifyIntent = new Intent(ChatActivity.MESSAGE_LOCAL_RECEIVER_ARG);
        boolean messageConsumed = LocalBroadcastManager.getInstance(context).sendBroadcast(notifyIntent);
        return messageConsumed ? null : super.getNotification(context, intent);
    }


}
