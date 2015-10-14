package com.pangea.examples.simplechat.parse;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.parse.ParsePushBroadcastReceiver;

public class SimpleChatPushBroadcastReceiver extends ParsePushBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
