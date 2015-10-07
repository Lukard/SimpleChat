package com.pangea.examples.simplechat.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.pangea.examples.simplechat.Message;

public class MessageViewModel extends BaseObservable {

    private Context context;
    private Message message;

    public MessageViewModel(Context context, Message message) {
        this.context = context;
        this.message = message;
    }
}
